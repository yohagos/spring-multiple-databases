package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.entities.TicketStatus;
import com.yohagos.multiple_databases.ticket.repositories.TicketStatusRepository;
import com.yohagos.multiple_databases.ticket.services.TicketStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketStatusServiceImpl implements TicketStatusService {

    private final TicketStatusRepository ticketStatusRepository;

    @Override
    public List<TicketStatus> loadTicketStatus() {
        return ticketStatusRepository.findAll();
    }
}
