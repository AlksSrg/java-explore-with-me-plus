package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto get(long userId, long commentId);

    List<CommentDto> getAll(long userId);

    CommentDto create(NewCommentDto comment);

    CommentDto update(UpdateCommentDto comment);

    void delete(long userId, long commentId);

    List<CommentDto> getComments(long eventId);
}
