package com.yohagos.multiple_databases.ticket.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private UUID commentId;
    private UUID ticketId;
    private String content;
    private UserResponse author;
    private LocalDateTime createdDate;
}
