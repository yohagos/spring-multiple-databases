package com.yohagos.multiple_databases.ticket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project extends BaseEntity {
    private String projectName;
    @Column(columnDefinition = "TEXT", length = 6000)
    private String projectDescription;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProjectStatus projectStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    private PriorityLevel priorityLevel;
    private String customerName;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> assignedUsers = new ArrayList<>();

    @Override
    public String toString() {
        return format("Project=[id=%s, projectName=%s, customerName=%s, projectStatus=%S, priorityLevel=%s, startDate=%s, endDate=%s]",
                getId(), getProjectName(), getCustomerName(), getProjectStatus().getProjectStatus(), getPriorityLevel().getPriorityLevel(), getProjectStartDate(),getProjectEndDate());
    }
}
