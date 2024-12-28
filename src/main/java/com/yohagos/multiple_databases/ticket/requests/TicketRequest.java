package com.yohagos.multiple_databases.ticket.requests;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.entities.Project;
import com.yohagos.multiple_databases.ticket.entities.TicketStatus;
import com.yohagos.multiple_databases.ticket.entities.TicketType;
import lombok.*;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private String ticketName;
    private String ticketDescription;
    private TicketStatus ticketStatus;
    private PriorityLevel priorityLevel;
    private TicketType ticketType;
    private Project project;
    private LocalDateTime dueDate;

    @Override
    public String toString() {
        return format("TicketRequest=[ticketName=%s, ticketStatus=%s, projectId=%s, projectName=%s, dueDate=%s]",
                getTicketName(), getTicketStatus(), getProject().getId(), getProject().getProjectName(), getDueDate());
    }
}
