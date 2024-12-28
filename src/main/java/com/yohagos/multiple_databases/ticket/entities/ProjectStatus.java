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
@Entity(name = "project_status")
public class ProjectStatus {
    @Id
    @GeneratedValue
    private UUID id;
    private String projectStatus;
    private String statusColor;

    public ProjectStatus(String status, String color) {
        projectStatus = status;
        statusColor = color;
    }
}
