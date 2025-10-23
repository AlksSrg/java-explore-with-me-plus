package ru.practicum.comment.controller.private_;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.service.CommentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("users/{userId}/comments")
@RequiredArgsConstructor
public class CommentPrivateController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto get(@PathVariable @Positive long userId,
                          @PathVariable @Positive long commentId) {
        return commentService.get(userId, commentId);
    }

    @GetMapping()
    public List<CommentDto> getAll(@PathVariable @Positive long userId) {
        return commentService.getAll(userId);
    }

    @PostMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable @Positive long userId,
                             @PathVariable @Positive long eventId,
                             @RequestBody @Valid NewCommentDto comment) {
        comment.setAuthor(userId);
        comment.setEvent(eventId);
        return commentService.create(comment);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(@PathVariable @Positive long userId,
                             @PathVariable @Positive long commentId,
                             @RequestBody @Valid UpdateCommentDto comment) {
        comment.setAuthor(userId);
        comment.setCommentId(commentId);
        return commentService.update(comment);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive long userId,
                       @PathVariable @Positive long commentId) {
        commentService.delete(userId, commentId);
    }
}
