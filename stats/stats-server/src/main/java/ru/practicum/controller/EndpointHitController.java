package ru.practicum.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import dto.ViewStatsRequestDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.service.EndpointHitService;

@Slf4j
@Validated
@RestController
public class EndpointHitController {
	
	private final EndpointHitService endpointHitService;

	public EndpointHitController(EndpointHitService endpointHitService) {
		this.endpointHitService = endpointHitService;
	}

	/*
	 * Сохранение информации о том, что на uri конкретного сервиса был отправлен
	 * запрос пользователем. Название сервиса, uri и ip пользователя указаны в теле
	 * запроса.
	 */
	@PostMapping("/hit")
	public ResponseEntity<String> postEndPointHit(@RequestBody @Valid EndpointHitDto endpointHit) {
		endpointHitService.addEndpointHit(endpointHit);
		return ResponseEntity.status(HttpStatus.CREATED).body("Информация сохранена");
	}

	/*
	 * Метод получения данных статистики по посещениям uri-адресов
	 */
	@GetMapping("/stats")
	public List<ViewStatsDto> getViewStats(@RequestBody ViewStatsRequestDto viewstats) {
		return endpointHitService.getViewStats(viewstats);
	}
}
