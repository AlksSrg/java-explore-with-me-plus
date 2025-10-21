package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с событиями.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Page<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findByIdIn(List<Long> eventIds);

    boolean existsByCategoryId(Long categoryId);
}