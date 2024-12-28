package com.yohagos.multiple_databases.ticket.apis;


import com.yohagos.multiple_databases.ticket.entities.TicketStatus;
import com.yohagos.multiple_databases.ticket.services.TicketStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket-status")
@RequiredArgsConstructor
@Tag(name = "Ticket Status Apis")
public class TicketStatusApi {

    private final TicketStatusService ticketStatusService;

    @GetMapping
    public ResponseEntity<List<TicketStatus>> getTicketStatus() {
        return ResponseEntity.ok(ticketStatusService.loadTicketStatus());
    }
}
