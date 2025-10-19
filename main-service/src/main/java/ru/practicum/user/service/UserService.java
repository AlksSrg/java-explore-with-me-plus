package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;
import ru.practicum.user.utill.UserGetParam;

import java.util.List;

public interface UserService {
    /**
     * Получает перечень пользователей.
     *
     * @param userGetParam параметры запроса
     * @return список пользователей
     */
    List<UserDto> getUsers(UserGetParam userGetParam);

    /**
     * Получение пользователя по ID.
     *
     * @param userId id пользователя
     * @return пользователь
     */
    User getUserById(Long userId);

    /**
     * Создание нового пользователя.
     *
     * @param newUserRequest данные нового пользователя
     * @return созданный пользователь
     */
    UserDto createUser(NewUserRequest newUserRequest);

    /**
     * Удаление пользователя.
     *
     * @param userId id пользователя
     */
    void deleteUser(Long userId);
}