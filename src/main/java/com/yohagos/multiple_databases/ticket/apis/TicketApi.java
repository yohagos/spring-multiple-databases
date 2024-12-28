package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.common.PageResponse;
import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.requests.TicketRequest;
import com.yohagos.multiple_databases.ticket.responses.TicketResponse;
import com.yohagos.multiple_databases.ticket.services.TicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "TicketApis")
public class TicketApi {

    private final TicketService ticketService;

    /*
    * Get Mapping
    * */

    @GetMapping
    public ResponseEntity<PageResponse<TicketResponse>> getTickets(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "15", required = false) int size,
            @RequestParam(name = "priorityLevel", required = false) PriorityLevel priorityLevel
    ) {
        return ResponseEntity.ok(ticketService.loadTickets(page, size));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByProjectId(
            @PathVariable(name = "projectId") UUID projectId
    ) {
        return ResponseEntity.ok(ticketService.loadTicketsByProjectId(projectId));
    }

    /*
    * Post Mapping
    * */

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createTicket(
            @RequestBody TicketRequest request
    ) {
        ticketService.createTicket(request);
        return ResponseEntity.status(CREATED).build();
    }

    /*
    * Put Mapping
    * */

    /*
    * Delete Mapping
    * */

    @DeleteMapping("/remove/{ticketId}")
    public ResponseEntity<HttpStatus> removeTicketById(
            @PathVariable(name = "ticketId") UUID ticketId
    ) {
        ticketService.deleteTicketById(ticketId);
        return ResponseEntity.status(ACCEPTED).build();
    }
}
