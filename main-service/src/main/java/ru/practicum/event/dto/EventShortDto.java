package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String eventDate;

	private UserShortDto initiator;
	private Boolean paid;
	private String title;
	private Long views;
}
