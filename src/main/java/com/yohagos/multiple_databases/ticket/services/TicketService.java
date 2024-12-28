package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.requests.TicketRequest;
import com.yohagos.multiple_databases.ticket.responses.TicketResponse;

import java.util.List;
import java.util.UUID;

public interface TicketService  {

    PageResponse<TicketResponse> loadTickets(int page, int size);

    void createTicket(TicketRequest request);

    List<TicketResponse> loadTicketsByProjectId(UUID projectId);

    void deleteTicketById(UUID ticketId);
}
