package com.yohagos.multiple_databases.ticket.mapper;

import com.yohagos.multiple_databases.ticket.entities.Ticket;
import com.yohagos.multiple_databases.ticket.requests.TicketRequest;
import com.yohagos.multiple_databases.ticket.responses.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketMapper {

    public TicketResponse toTicketResponse(
            Ticket entity
    ) {
        return TicketResponse.builder()
                .id(entity.getId())
                .ticketName(entity.getTicketName())
                .ticketDescription(entity.getTicketDescription())
                .ticketType(entity.getTicketType())
                .ticketStatus(entity.getTicketStatus())
                .priorityLevel(entity.getPriorityLevel())
                .project(entity.getProject())
                .dueDate(entity.getDueDate())
                .createdBy(entity.getCreatedBy())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public Ticket toTicket(
            TicketRequest request
    ) {
        return Ticket.builder()
                .ticketName(request.getTicketName())
                .ticketDescription(request.getTicketDescription())
                .ticketType(request.getTicketType())
                .ticketStatus(request.getTicketStatus())
                .priorityLevel(request.getPriorityLevel())
                .dueDate(request.getDueDate())
                .project(request.getProject())
                .build();
    }
}
