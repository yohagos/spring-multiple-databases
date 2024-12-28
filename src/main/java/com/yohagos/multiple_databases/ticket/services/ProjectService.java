package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.requests.ProjectRequest;
import com.yohagos.multiple_databases.ticket.responses.ProjectResponse;
import com.yohagos.multiple_databases.ticket.responses.UserResponse;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    PageResponse<ProjectResponse> loadProjects(int page, int size);

    void createProject(ProjectRequest request);

    void updateProject(UUID projectId, ProjectRequest request);
    void assignUserToProject(UUID projectId, UUID assignUserId);

    List<UserResponse> loadAssignedUsersByProjectId(UUID projectId);

    void deleteProjectById(UUID projectId);
}
