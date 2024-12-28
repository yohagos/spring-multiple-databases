package com.yohagos.multiple_databases.ticket.requests;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private UUID ticketId;
    private String content;
}
