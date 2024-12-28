package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.ticket.entities.TicketStatus;

import java.util.List;

public interface TicketStatusService {

    List<TicketStatus> loadTicketStatus();
}
