package ru.practicum.event.mappers;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

public class EventMapper {
    public static EventShortDto toEventShortDto(EventFullDto eventFullDto) {
        return EventShortDto.builder()
                .annotation(eventFullDto.getAnnotation())
                .category(eventFullDto.getCategory())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .eventDate(eventFullDto.getEventDate())
                .initiator(eventFullDto.getInitiator())
                .paid(eventFullDto.getPaid())
                .title(eventFullDto.getTitle())
                .views(eventFullDto.getViews())
                .build();
    }
}
