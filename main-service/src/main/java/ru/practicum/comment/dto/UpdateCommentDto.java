package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateCommentDto {
    @JsonIgnore
    private long author;

    @JsonIgnore
    private long commentId;
}
