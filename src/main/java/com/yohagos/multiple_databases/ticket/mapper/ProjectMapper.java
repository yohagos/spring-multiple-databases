package com.yohagos.multiple_databases.ticket.mapper;

import com.yohagos.multiple_databases.ticket.entities.Project;
import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import com.yohagos.multiple_databases.ticket.repositories.PriorityLevelRepository;
import com.yohagos.multiple_databases.ticket.repositories.ProjectStatusRepository;
import com.yohagos.multiple_databases.ticket.requests.ProjectRequest;
import com.yohagos.multiple_databases.ticket.responses.ProjectResponse;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectMapper {

    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;

    private final PriorityLevelRepository priorityLevelRepository;
    private final ProjectStatusRepository projectStatusRepository;

    public ProjectResponse toProjectResponse(
            Project entity
    ) {
        var project = ProjectResponse.builder()
                .id(entity.getId())
                .projectName(entity.getProjectName())
                .customerName(entity.getCustomerName())
                .projectStartDate(entity.getProjectStartDate())
                .projectEndDate(entity.getProjectEndDate())
                .projectDescription(entity.getProjectDescription())
                .projectStatus(entity.getProjectStatus())
                .priorityLevel(entity.getPriorityLevel())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
        /*if (entity.getTickets().size() >= 1) {
            var tickets = entity.getTickets().stream()
                    .map(ticketMapper::toTicketResponse)
                    .toList();
            project.setTickets(tickets);
        }*/
        if (entity.getAssignedUsers().size() >= 1) {
            var users = entity.getAssignedUsers().stream()
                    .map(userMapper::toUserResponse)
                    .toList();
            project.setAssignedUsers(users);
        }
        return project;
    }

    public Project toProject(ProjectRequest request) {
        return Project.builder()
                .projectName(request.getProjectName())
                .projectDescription(request.getProjectDescription())
                .customerName(request.getCustomerName())
                .projectStartDate(request.getProjectStartDate())
                .projectEndDate(request.getProjectEndDate())
                .projectStatus(request.getProjectStatus())
                .priorityLevel(request.getPriorityLevel())
                .build();
    }
}
