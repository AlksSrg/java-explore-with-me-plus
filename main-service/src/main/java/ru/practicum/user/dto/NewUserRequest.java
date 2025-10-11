package ru.practicum.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NewUserRequest {
    @NotBlank
    @Size(min = 6, max = 254, message = "Некорректное значение email")
    @Email(message = "Некорректный email")
    private String email;
    @NotBlank
    @Size(min = 2, max = 250, message = "Некорректное значение имени")
    private String name;
}
