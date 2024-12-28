package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.mapper.CommentMapper;
import com.yohagos.multiple_databases.ticket.repositories.CommentRepository;
import com.yohagos.multiple_databases.ticket.repositories.TicketRepository;
import com.yohagos.multiple_databases.ticket.repositories.UserRepository;
import com.yohagos.multiple_databases.ticket.requests.CommentRequest;
import com.yohagos.multiple_databases.ticket.responses.CommentResponse;
import com.yohagos.multiple_databases.ticket.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public void addCommentToTicket(CommentRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var userId = auth.getName();
        var user = userRepository.findUserByKeycloakId(userId).orElseThrow();
        commentRepository.save(commentMapper.toComment(request, user));
    }

    @Override
    public List<CommentResponse> getCommentsByTicketId(UUID ticketId) {
        var comments = commentRepository.findCommentsByTicketId(ticketId);
        return comments.stream().map(commentMapper::toCommentResponse).toList();
    }

    @Override
    public void editCommentById(UUID commentId, CommentRequest request) {
        var comment = commentRepository.findById(commentId).orElseThrow();
        comment.setContent(request.getContent());
        comment.setLastModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
