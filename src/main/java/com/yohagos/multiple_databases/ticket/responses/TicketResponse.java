package com.yohagos.multiple_databases.ticket.responses;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.Project;
import com.yohagos.multiple_databases.ticket.entities.TicketStatus;
import com.yohagos.multiple_databases.ticket.entities.TicketType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private UUID id;
    private String createdBy;
    private LocalDateTime createdDate;
    private String ticketName;
    private String ticketDescription;
    private TicketStatus ticketStatus;
    private Project project;
    private PriorityLevel priorityLevel;
    private TicketType ticketType;
    private LocalDateTime dueDate;
}
