package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.ticket.entities.TicketType;

import java.util.List;

public interface TicketTypeService {

    List<TicketType> loadTicketTypes();
}
