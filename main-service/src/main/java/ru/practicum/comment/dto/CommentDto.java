package ru.practicum.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class CommentDto {
    private long id;
    private User author;
    private Event event;
    private LocalDateTime created;
    private String text;
}
