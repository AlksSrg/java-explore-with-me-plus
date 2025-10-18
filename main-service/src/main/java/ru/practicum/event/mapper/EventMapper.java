package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EventMapper {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryIdToCategory")
    @Mapping(target = "initiator", source = "initiator", qualifiedByName = "userIdToUser")
    Event toEntity(NewEventDto newEventDto);

    // Методы с передачей confirmedRequests и views как параметров
    @Mapping(target = "confirmedRequests", source = "confirmedRequests")
    @Mapping(target = "views", source = "views")
    @Mapping(target = "eventDate", source = "event.eventDate", qualifiedByName = "formatLocalDateTime")
    @Mapping(target = "createdOn", source = "event.createdOn", qualifiedByName = "formatLocalDateTime")
    @Mapping(target = "publishedOn", source = "event.publishedOn", qualifiedByName = "formatLocalDateTime")
    EventFullDto toEventFullDto(Event event, Long confirmedRequests, Long views);

    @Mapping(target = "confirmedRequests", source = "confirmedRequests")
    @Mapping(target = "views", source = "views")
    @Mapping(target = "eventDate", source = "event.eventDate", qualifiedByName = "formatLocalDateTime")
    EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long views);

    // Альтернативные методы, которые используют поля из Event
    @Mapping(target = "eventDate", source = "eventDate", qualifiedByName = "formatLocalDateTime")
    @Mapping(target = "createdOn", source = "createdOn", qualifiedByName = "formatLocalDateTime")
    @Mapping(target = "publishedOn", source = "publishedOn", qualifiedByName = "formatLocalDateTime")
    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "eventDate", source = "eventDate", qualifiedByName = "formatLocalDateTime")
    EventShortDto toEventShortDto(Event event);

    @Named("formatLocalDateTime")
    default String formatLocalDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(FORMATTER);
    }

    @Named("categoryIdToCategory")
    default Category categoryIdToCategory(Long categoryId) {
        // Здесь возвращаем null, так как реальный объект Category будет устанавливаться в сервисе
        return null;
    }

    @Named("userIdToUser")
    default User userIdToUser(long userId) {
        // Здесь возвращаем null, так как реальный объект User будет устанавливаться в сервисе
        return null;
    }

    default CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    default UserShortDto toUserShortDto(User user) {
        if (user == null) {
            return null;
        }
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    // Статические методы для обратной совместимости
    static Event mapFromNewEventDto(NewEventDto newEventDto) {
        return Event.builder()
                .title(newEventDto.getTitle())
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .category(newEventDto.getCategoryObject())
                .eventDate(newEventDto.getEventDate())
                .initiator(newEventDto.getInitiatorObject())
                .location(newEventDto.getLocation())
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .createdOn(newEventDto.getCreatedOn())
                .build();
    }

    static EventFullDto mapToEventFullDto(Event event) { //, Long confirmedRequests, Long views) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.mapFromCategory(event.getCategory()))
                //.confirmedRequests(confirmedRequests != null ? confirmedRequests : 0L)
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(FORMATTER))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().format(FORMATTER) : null)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                //.views(views != null ? views : 0L)
                .views(event.getViews())
                .build();
    }

    static EventShortDto mapToEventShortDto(Event event) { //, Long confirmedRequests, Long views) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.mapFromCategory(event.getCategory()))
                //.confirmedRequests(confirmedRequests != null ? confirmedRequests : 0L)
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(FORMATTER))
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .title(event.getTitle())
                //.views(views != null ? views : 0L)
                .views(event.getViews())
                .build();
    }
}