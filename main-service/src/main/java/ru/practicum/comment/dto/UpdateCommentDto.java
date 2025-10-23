package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.user.model.User;

@Builder
@Getter
@Setter
public class UpdateCommentDto {
    @JsonIgnore
    private long author;

    @JsonIgnore
    private long commentId;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String text;

    @JsonIgnore
    private User authorObj;
}
