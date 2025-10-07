package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(UserGetParam userGetParam) {
        List<User> users;

        if (userGetParam.getIds() != null)
            users = userRepository.findAllByIdIn(userGetParam.getIds());
        else
            users = userRepository.findAll();

        return users == null ? List.of() : users.stream()
                .skip(userGetParam.getFrom() - 1)
                .limit(userGetParam.getSize() != null ? userGetParam.getSize() : Integer.MAX_VALUE)
                .map(UserMapper::mapToDto)
                .toList();
    }

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        if (userRepository.findByEmailContainingIgnoreCase(newUserRequest.getEmail()).isPresent())
            throw new ConflictResource("Пользователь с email - %s уже существует".formatted(newUserRequest.getEmail()));

        return UserMapper.mapToDto(userRepository.save(UserMapper.mapToUser(newUserRequest)));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isEmpty())
            throw new NotFoundResource("Пользователь с id - %d не найден".formatted(userId));

        userRepository.deleteById(userId);
    }
}
