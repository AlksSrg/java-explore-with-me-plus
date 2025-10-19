package ru.practicum.event.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.event.utill.State;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecification {
    public static Specification<Event> byUser(List<Long> users) {
        return (root, cq, cb) -> root.get("initiator").get("id").in(users);
    }

    public static Specification<Event> byStates(List<String> states) {
        return (root, cq, cb) -> root.get("state").as(String.class).in(states);
    }

    public static Specification<Event> byCategories(List<Long> categories) {
        return (root, cq, cb) -> root.get("category").get("id").in(categories);
    }

    public static Specification<Event> byRangeStart(LocalDateTime rangeStart) {
        return (root, cq, cb) ->
                cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
    }

    public static Specification<Event> byRangeEnd(LocalDateTime rangeEnd) {
        return (root, cq, cb) ->
                cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
    }
}
