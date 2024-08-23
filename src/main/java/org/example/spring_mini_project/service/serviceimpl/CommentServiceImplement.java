package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.repository.CommentRepository;
import org.example.spring_mini_project.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplement implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImplement(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
