package ru.practicum.event.utill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.dto.EventRequestStatusUpdateRequest;

@Builder
@Getter
@Setter
public class EventUpdateRequestParam {
    private long userId;
    private long eventId;
    private EventRequestStatusUpdateRequest eventRequestStatus;
}
