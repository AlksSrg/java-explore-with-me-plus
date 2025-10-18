package ru.practicum.request.mapper;

import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto mapToParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated().format(FORMATTER))
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public static Request mapToRequest(ParticipationRequestDto participationRequestDto) {
        return Request.builder()
                .id(participationRequestDto.getId())
                .created(participationRequestDto.getCreated() != null ?
                        LocalDateTime.parse(participationRequestDto.getCreated(), FORMATTER) :
                        LocalDateTime.now())
                .build();
        // Note: event and requester should be set separately as they are entities
    }
}