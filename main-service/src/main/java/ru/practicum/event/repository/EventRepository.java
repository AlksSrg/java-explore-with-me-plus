package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Page<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findByIdIn(List<Long> eventIds);

    boolean existsByCategoryId(Long categoryId);

//    @Query("SELECT e FROM Event e WHERE " +
//            "(:users IS NULL OR e.initiator.id IN :users) AND " +
//            "(:states IS NULL OR e.state IN :states) AND " +
//            "(:categories IS NULL OR e.category.id IN :categories) AND " +
//            "(:rangeStart IS NULL OR e.eventDate >= :rangeStart) AND " +
//            "(:rangeEnd IS NULL OR e.eventDate <= :rangeEnd)")
//    Page<Event> findEventsByAdmin(@Param("users") List<Long> users,
//                                  @Param("states") List<State> states,
//                                  @Param("categories") List<Long> categories,
//                                  @Param("rangeStart") LocalDateTime rangeStart,
//                                  @Param("rangeEnd") LocalDateTime rangeEnd,
//                                  Pageable pageable);

//    @Query("SELECT e FROM Event e WHERE " +
//            "(:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
//            "LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) AND " +
//            "(:categories IS NULL OR e.category.id IN :categories) AND " +
//            "(:paid IS NULL OR e.paid = :paid) AND " +
//            "(:rangeStart IS NULL OR e.eventDate >= :rangeStart) AND " +
//            "(:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) AND " +
//            "(:onlyAvailable IS NULL OR :onlyAvailable = false OR " +
//            "(e.participantLimit = 0 OR e.participantLimit > (SELECT COUNT(r) FROM Request r WHERE r.event = e AND r.status = 'CONFIRMED')))")
//    Page<Event> findEventsByPublic(@Param("text") String text,
//                                   @Param("categories") List<Long> categories,
//                                   @Param("paid") Boolean paid,
//                                   @Param("rangeStart") LocalDateTime rangeStart,
//                                   @Param("rangeEnd") LocalDateTime rangeEnd,
//                                   @Param("onlyAvailable") Boolean onlyAvailable,
//                                   Pageable pageable);

//    interface Specs {
//        static Specification<Event> byUser(List<Long> users) {
//            return (root, cq, cb) ->
//                    cb.in(root.get(Event_.))
//            root.get("initiator").get("id").in(users);
//    }
}