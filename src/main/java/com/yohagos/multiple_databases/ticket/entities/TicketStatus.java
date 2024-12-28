package com.yohagos.multiple_databases.ticket.entities;

import com.yohagos.multiple_databases.ticket.services.TicketService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

import static java.lang.String.format;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "ticket_status")
public class TicketStatus {
    @Id
    @GeneratedValue
    private UUID id;
    private String status;
    private String statusColor;

    public TicketStatus(String stat, String color) {
        status = stat;
        statusColor = color;
    }

    @Override
    public String toString() {
        return format("TicketStatus=[id=%s, status=%s, statusColor=%s]",
                getId(), getStatus(), getStatusColor());
    }
}
