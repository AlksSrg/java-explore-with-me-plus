package ru.practicum.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import dto.ViewStatsDto;

public class ViewStatsDtoRowMapper implements RowMapper<ViewStatsDto> {

	@Override
	public ViewStatsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		return ViewStatsDto.builder()
				.app(rs.getString("app"))
				.uri(rs.getString("uri"))
				.hits(rs.getLong("hits"))
				.build();
	}
}
