package ru.practicum.event.controller.public_;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;
import ru.practicum.event.utill.EventGetPublicParam;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequestMapping("/events")
@RestController
@RequiredArgsConstructor
public class EventPublicController {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String APPLICATION = "main-service";
    private static final String EVENT_URI_PATTERN = "/events/%d";
    private final EventService eventService;
    private final StatsClient statsClient;

    @GetMapping
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false)
            @DateTimeFormat(pattern =
                    DATE_TIME_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false)
            @DateTimeFormat(pattern =
                    DATE_TIME_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size,
            HttpServletRequest request) {

        EventGetPublicParam param = EventGetPublicParam.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                // если нет ограничений по дате начала, то позднее текущей
                .rangeStart(rangeStart == null && rangeEnd == null ? LocalDateTime.now() : rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();

        List<EventShortDto> events = eventService.getEventsByPublic(param);
        if (!events.isEmpty()) {
            List<String> uriList = events.stream()
                    .map(eventShortDto -> EVENT_URI_PATTERN.formatted(eventShortDto.getId()))
                    .toList();
            for (String uri : uriList)
                statsClient.saveStat(APPLICATION, uri, request.getRemoteAddr());
        }

        return events;
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable @Positive long id,
                                 HttpServletRequest request) {
        EventFullDto eventFullDto = eventService.getEventByPublic(id);
        statsClient.saveStat(APPLICATION, request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventByPublic(id);
    }
}