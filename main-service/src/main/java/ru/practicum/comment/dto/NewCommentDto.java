package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class NewCommentDto {
    @JsonIgnore
    private long author;

    @JsonIgnore
    private long event;

    @NotBlank
    @Size(min = 20, max = 5000)
    private String text;

    @JsonIgnore
    private final LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    private User authorObj;

    @JsonIgnore
    private Event eventObj;
}
