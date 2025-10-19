package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.Location;
import ru.practicum.event.utill.State;
import ru.practicum.user.dto.UserShortDto;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
	private String annotation;
	private CategoryDto category;
	private Long confirmedRequests;
	private String createdOn;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String eventDate;

	private Long id;
	private UserShortDto initiator;
	private Location location;
	private Boolean paid;

	@JsonProperty(defaultValue = "0")
	private Integer participantLimit;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String publishedOn;

	@JsonProperty(defaultValue = "true")
	private Boolean requestModeration;

	private State state;
	private String title;
	private Long views;
}
