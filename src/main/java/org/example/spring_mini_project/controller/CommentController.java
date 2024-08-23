package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.spring_mini_project.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
}
