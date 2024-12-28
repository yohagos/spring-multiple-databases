package com.yohagos.multiple_databases.ticket.entities;

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
@Entity(name = "ticket_type")
public class TicketType {
    @Id
    @GeneratedValue
    private UUID id;
    private String ticketType;

    public TicketType(String type) {
        ticketType = type;
    }

    @Override
    public String toString() {
        return format("TicketType=[id=%s, type=%s]",
                getId(), getTicketType());
    }
}
