package ru.practicum.event.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Table(name = "events")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String annotation;
    private String description;
    @ManyToOne
    private Category category;
    private LocalDateTime eventDate;
    @ManyToOne
    private User initiator;
    // TODO : координаты?
    //private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration = true;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
