package ru.practicum.event.utill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.dto.UpdateEventUserRequest;

@Builder
@Getter
@Setter
public class EventUpdateParameter {
    private long userId;
    private long eventId;
    private UpdateEventUserRequest updateEvent;
}
