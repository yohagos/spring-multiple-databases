package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.ticket.requests.CommentRequest;
import com.yohagos.multiple_databases.ticket.responses.CommentResponse;
import com.yohagos.multiple_databases.ticket.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Apis")
public class CommentApi {

    private final CommentService commentService;

    @GetMapping("/{ticketId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByTicketId(
            @PathVariable(name = "ticketId") UUID ticketId
    ) {
        return ResponseEntity.ok(commentService.getCommentsByTicketId(ticketId));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createCommentForTicket(
            @RequestBody CommentRequest request
    ) {
        commentService.addCommentToTicket(request);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<HttpStatus> updateCommentById(
            @PathVariable(name = "commentId") UUID commentId,
            @RequestBody CommentRequest request
    ) {
        commentService.editCommentById(commentId, request);
        return ResponseEntity.status(ACCEPTED).build();
    }
}
