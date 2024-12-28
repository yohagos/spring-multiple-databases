package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import com.yohagos.multiple_databases.ticket.services.ProjectStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project-status")
@RequiredArgsConstructor
@Tag(name = "ProjectStatusApis")
public class ProjectStatusApi {

    private final ProjectStatusService projectStatusService;

    @GetMapping
    public ResponseEntity<List<ProjectStatus>> getAvailableProjectStatus() {
        return ResponseEntity.ok(projectStatusService.loadProjectStatus());
    }
}
