package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import com.yohagos.multiple_databases.ticket.repositories.ProjectStatusRepository;
import com.yohagos.multiple_databases.ticket.services.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStatusServiceImpl implements ProjectStatusService {

    private final ProjectStatusRepository projectStatusRepository;

    @Override
    public List<ProjectStatus> loadProjectStatus() {
        return projectStatusRepository.findAll();
    }
}
