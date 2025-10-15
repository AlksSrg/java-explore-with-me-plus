package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.utill.EventGetParameter;
import ru.practicum.event.utill.EventUpdateParameter;
import ru.practicum.event.utill.EventUpdateRequestParam;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventService {
    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventFullDto get(long userId, long eventId);

    List<EventShortDto> getAll(EventGetParameter eventGetParameter);

    EventFullDto create(NewEventDto eventDto);

    EventFullDto update (EventUpdateParameter eventUpdateParameter);

    EventRequestStatusUpdateResult updateRequestStatus(EventUpdateRequestParam eventUpdateRequestParam);
}
