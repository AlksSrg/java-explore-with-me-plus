package ru.practicum.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiError {
    private String status;
    private String reason;
    private String message;
    private LocalDateTime timestamp;
}