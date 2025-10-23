package ru.practicum.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByAuthorIdAndEventId(long authorId, long eventId);

    List<Comment> findAllByAuthorId(long authorId);
}
