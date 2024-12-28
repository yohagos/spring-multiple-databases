package com.yohagos.multiple_databases.ticket.repositories;

import com.yohagos.multiple_databases.ticket.entities.Comment;
import com.yohagos.multiple_databases.ticket.responses.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("""
            SELECT ct
            FROM Comment ct
            WHERE ct.ticket.id = :ticketId
            """)
    List<Comment> findCommentsByTicketId(UUID ticketId);
}
