package ru.practicum.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
}