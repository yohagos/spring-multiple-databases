package com.yohagos.multiple_databases.ticket.entities;

import com.yohagos.multiple_databases.ticket.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private UUID id;
    private String message;
    private boolean isRead;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;
}
