package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriorityLevelRepository extends JpaRepository<PriorityLevel, UUID> {

    Optional<PriorityLevel> findByPriorityLevel(String level);
}
