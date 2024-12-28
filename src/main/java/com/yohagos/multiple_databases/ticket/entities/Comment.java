package com.yohagos.multiple_databases.ticket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends BaseEntity {
    @Column(columnDefinition = "TEXT", length = 2048)
    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity author;
}
