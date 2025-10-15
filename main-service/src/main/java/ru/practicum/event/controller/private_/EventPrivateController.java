package ru.practicum.event.controller.private_;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventService;
import ru.practicum.event.utill.EventGetParameter;
import ru.practicum.event.utill.EventUpdateParameter;
import ru.practicum.event.utill.EventUpdateRequestParam;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

@Validated
@RequestMapping("/users/{userId}/events")
@RestController
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventService eventService;

    @GetMapping("{eventId}/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable @Positive long userId,
                                                     @PathVariable @Positive long eventId) {
        return eventService.getRequests(userId, eventId);
    }

    @GetMapping("/{eventId}")
    public EventFullDto get(@PathVariable @Positive long userId,
                            @PathVariable @Positive long eventId) {
        return eventService.get(userId, eventId);
    }

    @GetMapping
    public List<EventShortDto> getAll(@PathVariable @Positive long userId,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                      @RequestParam(defaultValue = "10") @Positive int size) {
        return eventService.getAll(EventGetParameter.builder()
                .userId(userId)
                .from(from)
                .size(size)
                .build());
    }

    @PostMapping
    EventFullDto create(@PathVariable @Positive long userId,
                        @RequestBody @Valid NewEventDto eventDto) {
        eventDto.setInitiator(userId);
        return eventService.create(eventDto);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable @Positive long userId,
                               @PathVariable @Positive long eventId,
                               @RequestBody @Valid UpdateEventUserRequest updateEvent) {
        return eventService.update(EventUpdateParameter.builder()
                .userId(userId)
                .eventId(eventId)
                .updateEvent(updateEvent)
                .build());
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(@PathVariable @Positive long userId,
                                                              @PathVariable @Positive long eventId,
                                                              @RequestBody @Valid
                                                              EventRequestStatusUpdateRequest eventRequestStatus) {
        return eventService.updateRequestStatus(EventUpdateRequestParam.builder()
                .userId(userId)
                .eventId(eventId)
                .eventRequestStatus(eventRequestStatus)
                .build());
    }
}
