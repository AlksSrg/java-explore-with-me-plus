package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.Request;
import ru.practicum.request.utill.Status;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequesterId(Long requesterId);

    List<Request> findByEventId(Long eventId);

    List<Request> findByIdInAndEventId(List<Long> requestIds, Long eventId);

    Long countByEventIdAndStatus(Long eventId, Status status);

    Optional<Request> findByEventIdAndRequesterId(Long eventId, Long requesterId);

    List<Request> findAllByEventIdInAndStatus(Collection<Long> eventIds, Status status);

    List<Request> findByEventInitiatorIdAndEventId(Long initiatorId, Long eventId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    Long countConfirmedRequestsByEventId(@Param("eventId") Long eventId);

    boolean existsByEventIdAndRequesterId(Long eventId, Long requesterId);
}