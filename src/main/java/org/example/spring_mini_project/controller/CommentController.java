package org.example.spring_mini_project.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.spring_mini_project.model.request.CommentRequest;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        ApiResponse<?> response = new ApiResponse<>(
                "Get comment by id successfully",
                HttpStatus.OK,
                commentService.getCommentById(id),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        ApiResponse<?> response = new ApiResponse<>(
                "Delete comment by id successfully",
                HttpStatus.OK,
                commentService.deleteCommentById(id),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable Long id,@Valid @RequestBody CommentRequest commentRequest) {
        ApiResponse<?> response = new ApiResponse<>(
                "Get comment by id successfully",
                HttpStatus.OK,
                commentService.updateCommentById(id,commentRequest),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
