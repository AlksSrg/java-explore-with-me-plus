package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventFullDto get(long userId, long eventId);

    List<EventShortDto> getAll(long userId, int from, int size);

    EventFullDto create(long userId, NewEventDto eventDto);

    EventFullDto update (long userId, long eventId, UpdateEventUserRequest updateEvent);

    EventRequestStatusUpdateResult updateRequestStatus(long userId, long eventId,
                                                       EventRequestStatusUpdateRequest eventRequestStatus);
}
