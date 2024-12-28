package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query("""
            SELECT p
            FROM Project p
            """)
    Page<Project> findAllDisplayableProjects(Pageable pageable);
}
