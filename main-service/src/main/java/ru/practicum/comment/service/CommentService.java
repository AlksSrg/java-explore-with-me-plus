package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.util.List;

/**
 * Сервисный интерфейс для управления комментариями.
 * Определяет контракт для операций с комментариями пользователей к событиям.
 */
public interface CommentService {

    /**
     * Получает комментарий по идентификаторам пользователя и комментария.
     *
     * @param userId    идентификатор пользователя
     * @param commentId идентификатор комментария
     * @return DTO комментария
     */
    CommentDto get(long userId, long commentId);

    /**
     * Получает все комментарии указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список DTO комментариев пользователя
     */
    List<CommentDto> getAll(long userId);

    /**
     * Создает новый комментарий к событию.
     *
     * @param comment DTO с данными для создания комментария
     * @return созданный DTO комментария
     */
    CommentDto create(NewCommentDto comment);

    /**
     * Обновляет существующий комментарий.
     *
     * @param comment DTO с данными для обновления комментария
     * @return обновленный DTO комментария
     */
    CommentDto update(UpdateCommentDto comment);

    /**
     * Удаляет комментарий.
     *
     * @param userId    идентификатор пользователя
     * @param commentId идентификатор комментария
     */
    void delete(long userId, long commentId);

    /**
     * Получает все комментарии для указанного события.
     *
     * @param eventId идентификатор события
     * @return список DTO комментариев события
     */
    List<CommentDto> getComments(long eventId);
}