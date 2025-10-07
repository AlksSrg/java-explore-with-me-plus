package ru.practicum.complitation.dto;

import java.util.List;

import jakarta.validation.constraints.Positive;
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
	private Long id;

	private boolean pinned;
	private String title;
}
