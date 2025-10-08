package ru.practicum.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import dto.ViewStatsRequestDto;
import ru.practicum.exception.UnableAddElementException;
import ru.practicum.mapper.ViewStatsDtoRowMapper;

@Repository
public class EndpointHitRepositoryImpl implements EndpointHitRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public EndpointHitRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/*
	 * Сохранение информации о том, что на uri конкретного сервиса был отправлен
	 * запрос пользователем. Название сервиса, uri и ip пользователя указаны в теле
	 * запроса.
	 */
	@Override
	public void addEndpointHit(EndpointHitDto endpointHit) {
		// Сообщение для ошибки UnableAddElementException
		String errorMessage = "Неудачная попытка сохранения объекта в базу данных.";

		// Строка sql-запроса для namedParameterJdbcTemplate
		String addEndpointHitSql = ""
				+ "INSERT INTO stats (app, uri, ip, timestamp) "
				+ "VALUES (:app, :uri, :ip, :timeStamp)";

		// Набор параметров, передаваемых в namedParameterJdbcTemplate
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("app", endpointHit.getApp());
		sqlParams.addValue("uri", endpointHit.getUri());
		sqlParams.addValue("ip", endpointHit.getIp());
		sqlParams.addValue("timeStamp", endpointHit.getTimestamp());

		/*
		 * Условие возврата полученных данных / пробрасывания ошибки: колчество
		 * добавленых строк равно нулю - ошибка UnableAddElementException. колчество
		 * добавленых строк больше нуля - возврат полученных данных.
		 */
		if (namedParameterJdbcTemplate.update(addEndpointHitSql, sqlParams) == 0) {
			throw new UnableAddElementException(errorMessage, endpointHit);
		}
	}

	/*
	 * Метод получения данных статистики по посещениям uri-адресов
	 */
	@Override
	public List<ViewStatsDto> getViewStats(ViewStatsRequestDto viewStatsRequestDto) {
		
		// Строка sql-запроса для namedParameterJdbcTemplate
		MapSqlParameterSource sqlParams = new MapSqlParameterSource();
		sqlParams.addValue("start", viewStatsRequestDto.getStart());
		sqlParams.addValue("end", viewStatsRequestDto.getEnd());

		/*
		 * Сборка sql-запроса в зависимости от переданных параметров для фильтрации
		 */
		StringBuilder getViewStatsSql = new StringBuilder();
		
		// фильтрация по уникальным ip-адресам
		if (viewStatsRequestDto.getUnique()) {
			getViewStatsSql.append("SELECT s.app, s.uri, COUNT(DISTINCT s.ip) AS hits ");
		} else {
			getViewStatsSql.append("SELECT s.app, s.uri, COUNT(s.id) hits ");
		}
		
		// продолжение sql-запроса
		getViewStatsSql.append("FROM stats s ");

		// фильтрация заданному временному периоду (start - end)
		getViewStatsSql.append("WHERE s.timestamp BETWEEN :start AND :end ");

		// фильтрация по uri-адресам
		if (viewStatsRequestDto.getUris() != null && !viewStatsRequestDto.getUris().isEmpty()) {
			getViewStatsSql.append("AND s.uri IN :uris ");
			sqlParams.addValue("uris", viewStatsRequestDto.getUris());
		}

		// группировка полученных данных
		getViewStatsSql.append("GROUP BY s.app, s.uri ");

		// сортировка полученных данных
		getViewStatsSql.append("ORDER BY s.app, s.uri ");

		return namedParameterJdbcTemplate.query(getViewStatsSql.toString(), sqlParams, new ViewStatsDtoRowMapper());
	}
}
