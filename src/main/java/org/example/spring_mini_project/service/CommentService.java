package org.example.spring_mini_project.service;

import org.example.spring_mini_project.model.request.CommentRequest;

public interface CommentService {
    Object getCommentById(Long id);

    Object deleteCommentById(Long id);

    Object updateCommentById(Long id, CommentRequest commentRequest);
}
