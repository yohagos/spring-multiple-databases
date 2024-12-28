package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;

import java.util.List;

public interface ProjectStatusService {

    List<ProjectStatus> loadProjectStatus();
}
