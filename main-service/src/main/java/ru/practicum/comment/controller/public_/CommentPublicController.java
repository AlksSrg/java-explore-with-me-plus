package ru.practicum.comment.controller.public_;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class CommentPublicController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable @Positive long eventId) {
        return commentService.getComments(eventId);
    }
}
