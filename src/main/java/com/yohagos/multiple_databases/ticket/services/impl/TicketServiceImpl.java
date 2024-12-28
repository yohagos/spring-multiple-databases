package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.entities.Ticket;
import com.yohagos.multiple_databases.ticket.mapper.TicketMapper;
import com.yohagos.multiple_databases.ticket.repositories.CommentRepository;
import com.yohagos.multiple_databases.ticket.repositories.TicketRepository;
import com.yohagos.multiple_databases.ticket.requests.TicketRequest;
import com.yohagos.multiple_databases.ticket.responses.TicketResponse;
import com.yohagos.multiple_databases.ticket.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final CommentRepository commentRepository;

    @Override
    public PageResponse<TicketResponse> loadTickets(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Ticket> tickets = ticketRepository.findAllDisplayableTickets(pageable);
        List<TicketResponse> ticketResponses = tickets.stream()
                .map(ticketMapper::toTicketResponse)
                .toList();
        return new PageResponse<>(
                ticketResponses,
                tickets.getNumber(),
                tickets.getSize(),
                tickets.getTotalElements(),
                tickets.getTotalPages(),
                tickets.isFirst(),
                tickets.isLast()
        );
    }

    @Override
    public void createTicket(TicketRequest request) {
        var ticket = ticketMapper.toTicket(request);
        ticketRepository.save(ticket);
    }

    @Override
    public List<TicketResponse> loadTicketsByProjectId(UUID projectId) {
        return ticketRepository.findTicketsByProjectId(projectId);
    }

    @Override
    public void deleteTicketById(UUID ticketId) {
        var comments = commentRepository.findCommentsByTicketId(ticketId);
        if (!comments.isEmpty()) {
            commentRepository.deleteAll(comments);
            commentRepository.flush();
        }
        ticketRepository.deleteById(ticketId);
        ticketRepository.flush();
    }
}
