package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, UUID> {

    @Query("""
            Select skl
            From Skills skl
            Where skl.category = :category
            """)
    List<Skills> findByCategory(String category);
}
