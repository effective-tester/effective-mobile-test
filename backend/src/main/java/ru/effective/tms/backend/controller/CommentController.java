package ru.effective.tms.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective.tms.backend.annotation.security.IsAdminOrUserAssignee;
import ru.effective.tms.backend.dto.request.comment.CreateCommentRequest;
import ru.effective.tms.backend.dto.response.CommentDto;
import ru.effective.tms.backend.service.CommentService;
import ru.effective.tms.backend.validation.CreateGroup;

import java.util.UUID;

@RestController
@RequestMapping("/tasks/{taskId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    @IsAdminOrUserAssignee
    public ResponseEntity<CommentDto> addComment(
            @PathVariable UUID taskId,
            @Validated(CreateGroup.class) @RequestBody CreateCommentRequest request
    ) {
        CommentDto createdComment = commentService.addComment(taskId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdComment);
    }
}

