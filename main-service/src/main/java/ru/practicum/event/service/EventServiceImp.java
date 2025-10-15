package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.dto.*;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.service.UserService;
import ru.practicum.user.utill.UserGetParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImp implements EventService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        return List.of();
    }

    @Override
    public EventFullDto get(long userId, long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getAll(long userId, int from, int size) {
        return List.of();
    }

    @Override
    public EventFullDto create(long userId, NewEventDto eventDto) {
        eventDto.setCategoryObject(categoryService.get(eventDto.getCategory()).mapToCategory());
        eventDto.setInitiatorObject(UserMapper.mapFromDto(userService.getUsers(UserGetParam.builder()
                .ids(List.of(userId))
                .build()).getFirst()));
        return null;  //eventRepository.save(EventMapper.mapFromNewEventDto(eventDto));
    }

    @Override
    public EventFullDto update(long userId, long eventId, UpdateEventUserRequest updateEvent) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(long userId, long eventId,
                                                              EventRequestStatusUpdateRequest eventRequestStatus) {
        return null;
    }
}
