package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.event.utill.State;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventFullDto get(long userId, long eventId);

    List<EventShortDto> getAll(long userId, int from, int size);

    EventFullDto create(long userId, NewEventDto eventDto);

    EventFullDto update(long userId, long eventId, UpdateEventUserRequest updateEvent);

    EventRequestStatusUpdateResult updateRequestStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest eventRequestStatus);

    // Методы для администратора
    List<EventFullDto> getEventsByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateEventByAdmin(long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    // Методы для публичного API
    List<EventShortDto> getEventsByPublic(String text, List<Long> categories, Boolean paid,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getEventByPublic(long eventId);
}