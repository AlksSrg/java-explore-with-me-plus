package ru.practicum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import dto.ViewStatsRequestDto;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.repository.EndpointHitRepository;

@Slf4j
@Service
public class EndpointHitServiceImpl implements EndpointHitService {

	private final EndpointHitRepository endpointHitRepository;

	public EndpointHitServiceImpl(EndpointHitRepository endpointHitRepository) {
		this.endpointHitRepository = endpointHitRepository;
	}

	/*
	 * Сохранение информации о том, что на uri конкретного сервиса был отправлен
	 * запрос пользователем. Название сервиса, uri и ip пользователя указаны в теле
	 * запроса.
	 */
	@Override
	public void addEndpointHit(EndpointHitDto endpointHit) {
		log.info("Получен объект для сохранения: {}", endpointHit);
		endpointHitRepository.addEndpointHit(endpointHit);
		log.info("Объект {} сохранен", endpointHit);
	}

	/*
	 * Метод получения данных статистики по посещениям uri-адресов
	 */
	@Override
	public List<ViewStatsDto> getViewStats(ViewStatsRequestDto viewStatsRequestDto) {
		log.info("Получен объект для вызова статистики: {}", viewStatsRequestDto);
		List<ViewStatsDto> viewStatsResponse = endpointHitRepository.getViewStats(viewStatsRequestDto);
		log.info("Получен список объектов статистики: {}", viewStatsRequestDto);
		return viewStatsResponse;
	}
}
