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

    List<Request> findByEventId(Long eventId);

    List<Request> findByIdInAndEventId(List<Long> requestIds, Long eventId);

    Long countByEventIdAndStatus(Long eventId, Status status);

    Optional<Request> findByEventIdAndRequesterId(Long eventId, Long requesterId);

    List<Request> findByRequesterId(Long requesterId);

    @Query("SELECT r FROM Request r WHERE r.event.id IN :eventIds AND r.status = :status")
    List<Request> findAllByEventIdInAndStatus(@Param("eventIds") Collection<Long> eventIds,
                                              @Param("status") Status status);

    List<Request> findByEventIdAndStatus(Long eventId, Status status);

    boolean existsByRequesterIdAndEventId(Long requesterId, Long eventId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    Long countConfirmedRequestsByEventId(@Param("eventId") Long eventId);

    // Новые методы для улучшения функциональности
    List<Request> findByEventInitiatorIdAndEventId(Long initiatorId, Long eventId);

    @Query("SELECT r FROM Request r WHERE r.event.id = :eventId AND r.requester.id = :requesterId")
    Optional<Request> findByEventIdAndRequesterIdWithEvent(@Param("eventId") Long eventId,
                                                           @Param("requesterId") Long requesterId);

    @Query("SELECT COUNT(r) FROM Request r WHERE r.event.id = :eventId AND r.status = :status")
    Long countByEventIdAndStatusQuery(@Param("eventId") Long eventId, @Param("status") Status status);

    List<Request> findByStatusAndEventIdIn(Status status, List<Long> eventIds);

    @Query("SELECT r FROM Request r WHERE r.event.id = :eventId AND r.id IN :requestIds")
    List<Request> findByEventIdAndIdIn(@Param("eventId") Long eventId, @Param("requestIds") List<Long> requestIds);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long requesterId);
}