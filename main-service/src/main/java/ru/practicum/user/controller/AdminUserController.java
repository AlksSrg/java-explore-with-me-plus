package ru.practicum.user.controller;

import ru.practicum.user.admin.controller.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    @GetMapping
    public List<UserDto> getUsers() {
        return List.of();
    }
}
