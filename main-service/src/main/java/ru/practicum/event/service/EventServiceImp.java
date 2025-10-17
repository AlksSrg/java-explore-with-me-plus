package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.utill.State;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.request.utill.Status;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImp implements EventService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        Event event = getEventByIdAndInitiatorId(eventId, userId);
        return requestRepository.findByEventId(event.getId()).stream()
                .map(RequestMapper::mapToParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto get(long userId, long eventId) {
        Event event = getEventByIdAndInitiatorId(eventId, userId);
        Long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED);
        // Здесь должна быть логика подсчета просмотров из статистики
        Long views = 0L;
        return EventMapper.mapToEventFullDto(event, confirmedRequests, views);
    }

    @Override
    public List<EventShortDto> getAll(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        Map<Long, Event> eventMap = eventRepository.findByInitiatorId(userId, pageable).stream()
                .collect(Collectors.toMap(Event::getId, Function.identity()));
        Map<Long, Long> eventCountRequest = requestRepository.findAllByEventIdInAndStatus(eventMap.keySet(),
                        Status.CONFIRMED).stream()
                .collect(Collectors.groupingBy( request -> request.getEvent().getId(),
                        Collectors.counting()));

        return eventMap.values().stream()
                .map(event -> {
                    Long confirmedRequests = eventCountRequest.getOrDefault(event.getId(), 0L);
                    Long views = 0L; // Получать из статистики
                    return EventMapper.mapToEventShortDto(event, confirmedRequests, views);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto create(long userId, NewEventDto eventDto) {
// по идее это не нужно, аннотация CustomFuture должна перехватить
//        // Проверка времени события
//        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
//            throw new ConflictResource("Дата события должна быть не ранее чем через 2 часа от текущего момента");
//        }

        eventDto.setCategoryObject(categoryService.getCategoryById(eventDto.getCategory()));
        eventDto.setInitiatorObject(userService.getUserById(userId));

        Event event = EventMapper.mapFromNewEventDto(eventDto);
        Event savedEvent = eventRepository.save(event);

        return EventMapper.mapToEventFullDto(savedEvent, 0L, 0L);
    }

    @Override
    @Transactional
    public EventFullDto update(long userId, long eventId, UpdateEventUserRequest updateEvent) {
        Event event = getEventByIdAndInitiatorId(eventId, userId);

        // Проверка состояния события
        if (event.getState() == State.PUBLISHED) {
            throw new ConflictResource("Нельзя редактировать опубликованное событие");
        }

        // Проверка времени события
        if (updateEvent.getEventDate() != null &&
                updateEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictResource("Дата события должна быть не ранее чем через 2 часа от текущего момента");
        }

        updateEventFields(event, updateEvent);

        // Обработка stateAction
        if (updateEvent.getStateAction() != null) {
            switch (updateEvent.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(State.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(State.CANCELED);
                    break;
            }
        }

        Event updatedEvent = eventRepository.save(event);
        Long confirmedRequests = requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED);
        Long views = 0L; // Получать из статистики

        return EventMapper.mapToEventFullDto(updatedEvent, confirmedRequests, views);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestStatus(long userId, long eventId,
                                                              EventRequestStatusUpdateRequest eventRequestStatus) {
        Event event = getEventByIdAndInitiatorId(eventId, userId);

        // Проверка модерации
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConflictResource("Подтверждение заявок не требуется для этого события");
        }

        Long confirmedCount = requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED);
        if (eventRequestStatus.getStatus() == Status.CONFIRMED &&
                confirmedCount >= event.getParticipantLimit()) {
            throw new ConflictResource("Достигнут лимит участников");
        }

        List<Request> requests = requestRepository.findByIdInAndEventId(eventRequestStatus.getRequestIds(), eventId);
        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        for (Request request : requests) {
            if (request.getStatus() != Status.PENDING) {
                throw new ConflictResource("Можно изменить статус только заявок в состоянии ожидания");
            }

            if (eventRequestStatus.getStatus() == Status.CONFIRMED &&
                    confirmedCount < event.getParticipantLimit()) {
                request.setStatus(Status.CONFIRMED);
                confirmed.add(RequestMapper.mapToParticipationRequestDto(request));
                confirmedCount++;
            } else {
                request.setStatus(Status.REJECTED);
                rejected.add(RequestMapper.mapToParticipationRequestDto(request));
            }
        }

        requestRepository.saveAll(requests);

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmed)
                .rejectedRequests(rejected)
                .build();
    }

    // Вспомогательные методы
    private Event getEventByIdAndInitiatorId(long eventId, long userId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundResource("Событие с id=" + eventId + " не найдено"));
    }

    private void updateEventFields(Event event, UpdateEventUserRequest updateEvent) {
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getCategory() != null) {
            event.setCategory(categoryService.getCategoryById(updateEvent.getCategory()));
        }
        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(updateEvent.getEventDate());
        }
        if (updateEvent.getLocation() != null) {
            event.setLocation(updateEvent.getLocation());
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getRequestModeration() != null) {
            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }
    }

    // Реализации методов для администратора и публичного API
    @Override
    public List<EventFullDto> getEventsByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsByPublic(String text, List<Long> categories, Boolean paid,
                                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                 Boolean onlyAvailable, String sort, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto getEventByPublic(long eventId) {
        return null;
    }
}