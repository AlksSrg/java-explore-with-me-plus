package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.request.utill.Status;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String created;

	private Long event;
	private Long id;
	private Long requester;
	private Status status;
}
