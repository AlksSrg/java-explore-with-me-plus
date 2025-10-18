package ru.practicum.complitation.dto;

import java.util.List;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.event.dto.EventShortDto;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;

    @Positive
    @NotNull
    private Long id;

    @NotNull
    private Boolean pinned;

    @NotBlank
    @NotNull
    private String title;
}