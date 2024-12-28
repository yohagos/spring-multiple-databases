package com.yohagos.multiple_databases.ticket.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "priority_level")
public class PriorityLevel {
    @Id
    @GeneratedValue
    private UUID id;
    private String priorityLevel;
    private String priorityColor;

    public PriorityLevel(String level, String color) {
        priorityLevel = level;
        priorityColor = color;
    }
}
