package ru.practicum.event.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.request.dto.ParticipationRequestDto;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
	private List<ParticipationRequestDto> confirmedRequests;
	private List<ParticipationRequestDto> rejectedRequests;
}
