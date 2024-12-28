package com.yohagos.multiple_databases.ticket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket extends BaseEntity {
    private String ticketName;
    @Column(columnDefinition = "TEXT", length = 6000)
    private String ticketDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    private TicketStatus ticketStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    private PriorityLevel priorityLevel;
    @ManyToOne(fetch = FetchType.EAGER)
    private TicketType ticketType;
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ticket_user",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> assignedUsers;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Override
    public String toString() {
        return format("Ticket=[id=%s, ticketName=%s, ticketStatus=%s, projectId=%s, projectName=%s]",
                getId(), getTicketName(), getTicketStatus(), getProject().getId(), getProject().getProjectName());
    }
}
