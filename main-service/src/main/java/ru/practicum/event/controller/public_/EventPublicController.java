package ru.practicum.event.controller.public_;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequestMapping("/events")
@RestController
@RequiredArgsConstructor
public class EventPublicController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {

        return eventService.getEventsByPublic(text, categories, paid, rangeStart,
                rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable @Positive long id) {
        return eventService.getEventByPublic(id);
    }
}