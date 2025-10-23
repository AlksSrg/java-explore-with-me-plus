package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.event.utill.State;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.ForbiddenResource;
import ru.practicum.exception.NotFoundResource;
import ru.practicum.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService{
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    public CommentDto get(long userId, long commentId) {
        // проверка
        userService.getUserById(userId);

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty())
            throw new NotFoundResource("Комментарий с id - %d не найден ".formatted(commentId));

        Comment comment = optionalComment.get();
        if (!comment.getAuthor().getId().equals(userId))
            throw new ForbiddenResource("Просмотр комментария другого автора не возможен");

        return CommentMapper.mapFromComment(comment);
    }

    @Override
    public List<CommentDto> getAll(long userId) {
        // проверка
        userService.getUserById(userId);

        return commentRepository.findAllByAuthorId(userId).stream()
                .map(CommentMapper::mapFromComment)
                .toList();
    }

    @Override
    @Transactional
    public CommentDto create(NewCommentDto comment) {
        // проверяем что еще нет комментария этого пользователя на данное событие
        if (commentRepository.existsByAuthorIdAndEventId(comment.getAuthor(), comment.getEvent()))
            throw new ConflictResource("Уже существует комментарий пользователя к данному событию");

        comment.setEventObj(eventService.getEventById(comment.getEvent()));
        // комментировать можно только опубликованное событие
        if (!comment.getEventObj().getState().equals(State.PUBLISHED))
            throw new ConflictResource("Комментировать можно только опубликованное событие");

        comment.setAuthorObj(userService.getUserById(comment.getAuthor()));

        return CommentMapper.mapFromComment(commentRepository.save(CommentMapper.mapFromNewDto(comment)));
    }

    @Override
    public CommentDto update(UpdateCommentDto comment) {
        // TODO : реализовать
        return null;
    }

    @Override
    public void delete(long userId, long commentId) {
        // проверка
        userService.getUserById(userId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty())
            throw new NotFoundResource("Комментарий с id - %d не найден ".formatted(commentId));

        Comment comment = optionalComment.get();
        if (!comment.getAuthor().getId().equals(userId))
            throw new ForbiddenResource("Удаление комментария другого автора не возможен");
    }

    @Override
    public List<CommentDto> getComments(long eventId) {
        // TODO : реализовать
        return List.of();
    }
}
