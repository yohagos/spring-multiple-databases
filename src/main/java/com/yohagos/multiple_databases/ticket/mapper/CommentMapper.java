package com.yohagos.multiple_databases.ticket.mapper;

import com.yohagos.multiple_databases.ticket.entities.Comment;
import com.yohagos.multiple_databases.ticket.entities.UserEntity;
import com.yohagos.multiple_databases.ticket.repositories.TicketRepository;
import com.yohagos.multiple_databases.ticket.repositories.UserRepository;
import com.yohagos.multiple_databases.ticket.requests.CommentRequest;
import com.yohagos.multiple_databases.ticket.responses.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TicketRepository ticketRepository;

    public CommentResponse toCommentResponse(Comment entity) {
        var com = CommentResponse.builder()
                .commentId(entity.getId())
                .content(entity.getContent())
                .ticketId(entity.getTicket().getId())
                .createdDate(entity.getCreatedDate())
                .build();

        if (entity.getAuthor() != null) {
            com.setAuthor(userMapper.toUserResponse(entity.getAuthor()));
        }

        return com;
    }

    public Comment toComment(CommentRequest request, UserEntity user) {
        var ticket = ticketRepository.findById(request.getTicketId()).orElseThrow();
        return Comment.builder()
                .content(request.getContent())
                .ticket(ticket)
                .author(user)
                .build();
    }
}
