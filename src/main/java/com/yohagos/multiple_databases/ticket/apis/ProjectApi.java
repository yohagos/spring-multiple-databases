package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.entities.UserEntity;
import com.yohagos.multiple_databases.ticket.requests.ProjectRequest;
import com.yohagos.multiple_databases.ticket.responses.ProjectResponse;
import com.yohagos.multiple_databases.ticket.responses.UserResponse;
import com.yohagos.multiple_databases.ticket.services.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "ProjectApis")
public class ProjectApi {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<PageResponse<ProjectResponse>> getProjects(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(projectService.loadProjects(page, size));
    }

    @GetMapping("/assigned-users/{projectId}")
    public ResponseEntity<List<UserResponse>> getAssignedUsers(
            @PathVariable(name = "projectId") UUID projectId
    ) {
        return ResponseEntity.ok(projectService.loadAssignedUsersByProjectId(projectId));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> addProject(
            @RequestBody ProjectRequest request
    ) {
        projectService.createProject(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<HttpStatus> updateProject(
            @PathVariable(name = "projectId") UUID projectId,
            @RequestBody ProjectRequest request
    ) {
        projectService.updateProject(projectId, request);
        return ResponseEntity.status(ACCEPTED).build();
    }

    @PutMapping("/assign/{projectId}/{userId}")
    public ResponseEntity<HttpStatus> assignUserToProject(
            @PathVariable(name = "projectId") UUID projectId,
            @PathVariable(name = "userId") UUID userId
    ) {
        projectService.assignUserToProject(projectId, userId);
        return ResponseEntity.status(ACCEPTED).build();
    }

    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<HttpStatus> deleteProject(
            @PathVariable(name = "projectId") UUID projectId
    ) {
        projectService.deleteProjectById(projectId);
        return ResponseEntity.status(ACCEPTED).build();
    }
}
