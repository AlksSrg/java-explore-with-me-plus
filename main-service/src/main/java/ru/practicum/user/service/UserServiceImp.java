package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.utill.UserGetParam;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    /**
     * Получает перечень пользователей.
     *
     * @param userGetParam параметры запроса
     * @return список пользователей
     */
    @Override
    public List<UserDto> getUsers(UserGetParam userGetParam) {
        List<User> users;

        if (userGetParam.getIds() != null && !userGetParam.getIds().isEmpty()) {
            users = userRepository.findAllByIdIn(userGetParam.getIds());
        } else {
            Pageable pageable = PageRequest.of(userGetParam.getFrom() / userGetParam.getSize(), userGetParam.getSize());
            users = userRepository.findAll(pageable).getContent();
        }

        return users.stream()
                .map(UserMapper::mapToDto)
                .toList();
    }

    /**
     * Получение пользователя по ID.
     *
     * @param userId id пользователя
     * @return пользователь
     */
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundResource("Пользователь с id=" + userId + " не найден"));
    }

    /**
     * Создание нового пользователя.
     *
     * @param newUserRequest данные нового пользователя
     * @return созданный пользователь
     */
    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        // Проверка уникальности email
        userRepository.findByEmailContainingIgnoreCase(newUserRequest.getEmail())
                .ifPresent(user -> {
                    throw new ConflictResource("Пользователь с email '" + newUserRequest.getEmail() + "' уже существует");
                });

        User user = UserMapper.mapToUser(newUserRequest);
        User savedUser = userRepository.save(user);

        return UserMapper.mapToDto(savedUser);
    }

    /**
     * Удаление пользователя.
     *
     * @param userId id пользователя
     */
    @Override
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Long userId) {
        // Проверка существования пользователя
        if (!userRepository.existsById(userId)) {
            throw new NotFoundResource("Пользователь с id=" + userId + " не найден");
        }

        userRepository.deleteById(userId);
    }
}