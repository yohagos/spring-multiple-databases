package com.yohagos.multiple_databases.ticket.requests;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import lombok.*;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    private String projectName;
    private String projectDescription;
    private String customerName;
    private ProjectStatus projectStatus;
    private PriorityLevel priorityLevel;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;

    @Override
    public String toString() {
        return format("ProjectRequest=[projectName=%s, customerName=%s, projectStatus=%s, priorityLevel=%s, projectStartDate=%s, projectEndDate=%s]",
                getProjectName(), getCustomerName(), getProjectStatus(), getPriorityLevel(), getProjectStartDate(), getProjectEndDate());
    }
}
