package ru.practicum.event.service;

import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.utill.EventGetParameter;
import ru.practicum.event.utill.EventUpdateParameter;
import ru.practicum.event.utill.EventUpdateRequestParam;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Service
public class EventServiceImp implements EventService {
    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        return List.of();
    }

    @Override
    public EventFullDto get(long userId, long eventId) {
        return null;
    }

    @Override
    public List<EventShortDto> getAll(EventGetParameter eventGetParameter) {
        return List.of();
    }

    @Override
    public EventFullDto create(NewEventDto eventDto) {
        return null;
    }

    @Override
    public EventFullDto update(EventUpdateParameter eventUpdateParameter) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(EventUpdateRequestParam eventUpdateRequestParam) {
        return null;
    }
}
