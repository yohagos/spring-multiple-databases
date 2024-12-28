package com.yohagos.multiple_databases.system.monitoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MonitoringRepository extends JpaRepository<Monitoring, UUID> {

    @Query("""
            SELECT m
            FROM Monitoring m
            ORDER BY m.createdAt DESC
            LIMIT 10
            """)
    List<Monitoring> findTopFiftyByOrderByCreatedAtDesc();
}
