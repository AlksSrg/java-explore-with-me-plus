package ru.practicum.event.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.user.dto.UserShortDto;

public class EventRowMapper implements RowMapper<EventFullDto> {

    @Override
    public EventFullDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        String eventDate = rs.getTimestamp("date").toString();

        UserShortDto initiator = UserShortDto.builder()
                .id(rs.getLong("initiator_id"))
                .name(rs.getString("initiator_name"))
                .build();

        CategoryDto category = CategoryDto.builder()
                .id(rs.getLong("category_id"))
                .name(rs.getString("category_name"))
                .build();

        return EventFullDto.builder()
                .annotation(rs.getString("annotation"))
                .category(category)
                .confirmedRequests(rs.getLong("confirmedRequestst"))
                .eventDate(eventDate)
                .id(rs.getLong("id"))
                .initiator(initiator)
                .paid(rs.getBoolean("paid"))
                .title(rs.getString("title"))
                .views(rs.getLong("views"))
                .build();
    }
}
