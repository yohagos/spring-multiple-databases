package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.entities.TicketType;
import com.yohagos.multiple_databases.ticket.repositories.TicketTypeRepository;
import com.yohagos.multiple_databases.ticket.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public List<TicketType> loadTicketTypes() {
        return ticketTypeRepository.findAll();
    }
}
