package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.Ticket;
import com.yohagos.multiple_databases.ticket.responses.TicketResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("""
            Select t
            FROM Ticket t
            """)
    Page<Ticket> findAllDisplayableTickets(Pageable pageable);

    @Query("""
            Select tick
            From Ticket tick
            Where tick.project.id = :projectId
            """)
    List<TicketResponse> findTicketsByProjectId(UUID projectId);
}
