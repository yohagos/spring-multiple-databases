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
@Entity
public class Skills {
    @Id
    @GeneratedValue
    private UUID id;
    private String skillName;
    private String category;

    public Skills(String skill, String cat) {
        skillName = skill;
        category = cat;
    }
}
