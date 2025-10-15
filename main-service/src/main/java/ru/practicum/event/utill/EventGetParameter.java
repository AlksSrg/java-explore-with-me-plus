package ru.practicum.event.utill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EventGetParameter {
    private long userId;
    private int from;
    private int size;
}
