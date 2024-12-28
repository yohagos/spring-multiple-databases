package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.ticket.requests.CommentRequest;
import com.yohagos.multiple_databases.ticket.responses.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    void addCommentToTicket(CommentRequest request);

    List<CommentResponse> getCommentsByTicketId(UUID ticketId);

    void editCommentById(UUID commentId, CommentRequest request);
}
