package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "Краткое описание события должно быть от 20 до 2000 символов")
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000, message = "Полное описание события должно быть от 20 до 7000 символов")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    // TODO: ??????
    private String stateAction;
    @Size(min = 3, max = 120, message = "Заголовок события должен быть от 3 до 120 символов")
    private String title;
}
