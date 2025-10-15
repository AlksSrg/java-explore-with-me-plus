package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class NewEventDto {
    @NotBlank(message = "Краткое описание должно быть заполнено")
    @Size(min = 20, max = 2000, message = "Краткое описание события должно быть от 20 до 2000 символов")
    private String annotation;
    @NotNull(message = "id категории обязательно для ввода")
    @Positive(message = "id категории должно быть больше 0")
    private Long category;
    @NotBlank(message = "Полное описание должно быть заполнено")
    @Size(min = 20, max = 7000, message = "Полное описание события должно быть от 20 до 7000 символов")
    private String description;
    @NotNull(message = "Дата и время должны быть заполнены")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull(message = "Широта и долгота должны быть заполнены")
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration = true;
    @NotBlank(message = "Заголовок события должен быть заполнен")
    @Size(min = 3, max = 120, message = "Заголовок события должен быть от 3 до 120 символов")
    private String title;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long initiator;
}
