package ru.practicum.event.mapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

public class EventMapper {
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
                .build();
    }

    public static EventFullDto mapFromEventToFullDto(Event event) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryDto.mapFromCategory(event.getCategory()))
                // TODO : реализовать
                //.confirmedRequests(event.)
                .createdOn()
                .build();
    }
}
