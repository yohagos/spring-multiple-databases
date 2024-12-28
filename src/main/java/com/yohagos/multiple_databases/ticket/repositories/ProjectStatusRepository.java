package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, UUID> {

    Optional<ProjectStatus> findByProjectStatus(String status);
}
