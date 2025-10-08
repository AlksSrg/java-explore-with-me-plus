package ru.practicum.service;

import java.util.List;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import dto.ViewStatsRequestDto;

public interface EndpointHitService {

	/*
	 * Сохранение информации о том, что на uri конкретного сервиса был отправлен
	 * запрос пользователем. Название сервиса, uri и ip пользователя указаны в теле
	 * запроса.
	 */
	void addEndpointHit(EndpointHitDto endpointHit);

	/*
	 * Метод получения данных статистики по посещениям uri-адресов
	 */
	List<ViewStatsDto> getViewStats(ViewStatsRequestDto viewStatsRequestDto);
}