package com.yohagos.multiple_databases.ticket.responses;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private UUID id;
    private String projectName;
    private String projectDescription;
    private String customerName;
    private ProjectStatus projectStatus;
    private PriorityLevel priorityLevel;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
    private List<UserResponse> assignedUsers;
}
