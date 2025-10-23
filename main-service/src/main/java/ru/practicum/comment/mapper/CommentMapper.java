package ru.practicum.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.user.mapper.UserMapper;

@UtilityClass
public class CommentMapper {
    public static Comment mapFromNewDto(NewCommentDto commentDto) {
        return Comment.builder()
                .author(commentDto.getAuthorObj())
                .event(commentDto.getEventObj())
                .created(commentDto.getCreated())
                .text(commentDto.getText())
                .build();
    }

    public static CommentDto mapFromComment(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(UserMapper.mapToDto(comment.getAuthor()))
                .event(EventMapper.mapToEventShortDto(comment.getEvent()))
                .created(comment.getCreated())
                .text(comment.getText())
                .build();
    }
}
