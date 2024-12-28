package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.entities.Project;
import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import com.yohagos.multiple_databases.ticket.mapper.ProjectMapper;
import com.yohagos.multiple_databases.ticket.mapper.UserMapper;
import com.yohagos.multiple_databases.ticket.repositories.ProjectRepository;
import com.yohagos.multiple_databases.ticket.repositories.UserRepository;
import com.yohagos.multiple_databases.ticket.requests.ProjectRequest;
import com.yohagos.multiple_databases.ticket.responses.ProjectResponse;
import com.yohagos.multiple_databases.ticket.responses.UserResponse;
import com.yohagos.multiple_databases.ticket.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public PageResponse<ProjectResponse> loadProjects(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Project> projects = projectRepository.findAllDisplayableProjects(pageable);
        List<ProjectResponse> projectResponses = projects.stream()
                .map(projectMapper::toProjectResponse)
                .toList();
        return new PageResponse<>(
                projectResponses,
                projects.getNumber(),
                projects.getSize(),
                projects.getTotalElements(),
                projects.getTotalPages(),
                projects.isFirst(),
                projects.isLast()
        );
    }

    @Override
    public void createProject(ProjectRequest request) {
        var project = projectRepository.save(projectMapper.toProject(request));
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var userId = authentication.getName();
        var user = userRepository.findUserByKeycloakId(userId).orElseThrow();

        assignUserToProject(project.getId(), user.getId());
    }

    @Override
    public List<UserResponse> loadAssignedUsersByProjectId(UUID projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        return project.getAssignedUsers().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public void updateProject(UUID projectId, ProjectRequest request) {
        var project = projectRepository.findById(projectId).orElseThrow();

        Optional.of(request.getProjectName())
                .filter(name -> !name.isEmpty() || !name.equals(project.getProjectName()))
                .ifPresent(project::setProjectName);
        Optional.of(request.getProjectDescription())
                .filter(desc -> !desc.isEmpty() || !desc.equals(project.getProjectDescription()))
                .ifPresent(project::setProjectDescription);
        Optional.of(request.getCustomerName())
                .filter(name -> !name.isEmpty() || !name.equals(project.getCustomerName()))
                .ifPresent(project::setCustomerName);
        Optional.of(request.getProjectStatus())
                .filter(stat -> !stat.equals(project.getProjectStatus()))
                .ifPresent(project::setProjectStatus);
        Optional.of(request.getPriorityLevel())
                .filter(prio -> !prio.equals(project.getPriorityLevel()))
                .ifPresent(project::setPriorityLevel);
        Optional.of(request.getProjectStartDate())
                .filter(startDate -> !startDate.equals(project.getProjectStartDate()))
                .ifPresent(project::setProjectStartDate);
        Optional.of(request.getProjectEndDate())
                .filter(endDate -> !endDate.equals(project.getProjectEndDate()))
                .ifPresent(project::setProjectEndDate);
        projectRepository.save(project);
    }

    @Override
    public void assignUserToProject(UUID projectId, UUID assignUserId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        var user = userRepository.findById(assignUserId).orElseThrow();
        var assignedUsers = project.getAssignedUsers();
        assignedUsers.add(user);
        project.setAssignedUsers(assignedUsers);
        projectRepository.save(project);
    }

    @Override
    public void deleteProjectById(UUID projectId) {
        removeAllEntriesByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }

    private void removeAllEntriesByProjectId(UUID projectId) {
        var project = projectRepository.findById(projectId).orElseThrow();
        System.out.println("Count assigned users (before) : " + project.getAssignedUsers());
        project.setAssignedUsers(null);
        projectRepository.save(project);
        System.out.println("Count assigned users (after) : " + project.getAssignedUsers());
    }
}
