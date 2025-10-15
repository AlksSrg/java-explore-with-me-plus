package ru.practicum.event.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.user.mapper.UserMapper;

import java.time.format.DateTimeFormatter;

public class EventMapper {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event mapFromNewEventDto(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(newEventDto.getCategoryObject())
                .eventDate(newEventDto.getEventDate())
                .initiator(newEventDto.getInitiatorObject())
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .lat(newEventDto.getLocation().getLat())
                .lon(newEventDto.getLocation().getLon())
                .build();
    }

    public static EventFullDto mapFromEventToFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryDto.mapFromCategory(event.getCategory()))
                // TODO : реализовать
                //.confirmedRequests(event.)
                .createdOn(event.getCreatedOn().format(DATE_TIME_FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DATE_TIME_FORMATTER))
                .id(event.getId())
                .initiator(UserMapper.mapToUserShortDto(event.getInitiator()))
                .location(Location.builder()
                        .lat(event.getLat())
                        .lon(event.getLon())
                        .build())
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(DATE_TIME_FORMATTER))
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                // TODO :  реализовать
                //.views()
                .build();
    }
}
