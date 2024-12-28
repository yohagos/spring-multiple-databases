package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.ticket.entities.TicketType;
import com.yohagos.multiple_databases.ticket.services.TicketTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket-types")
@RequiredArgsConstructor
@Tag(name = "Ticket Types Apis")
public class TicketTypesApi {

    private final TicketTypeService ticketTypeService;

    @GetMapping
    public ResponseEntity<List<TicketType>> getTicketTypes() {
        return ResponseEntity.ok(ticketTypeService.loadTicketTypes());
    }
}
