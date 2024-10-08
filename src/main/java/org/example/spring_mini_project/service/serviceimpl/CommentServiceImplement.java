package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.exception.ForbiddenException;
import org.example.spring_mini_project.exception.NotFoundException;
import org.example.spring_mini_project.model.entity.Comment;
import org.example.spring_mini_project.model.request.CommentRequest;
import org.example.spring_mini_project.model.response.CommentResponse;
import org.example.spring_mini_project.repository.CommentRepository;
import org.example.spring_mini_project.service.CommentService;
import org.example.spring_mini_project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplement implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentServiceImplement(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public CommentResponse getCommentById(Long id) {
        Optional<Comment> comment = Optional.ofNullable(commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found")));
        return comment.get().toResponse();
    }

    @Override
    public Comment deleteCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if(!comment.getUser().getUserId().equals(userService.getCurrentUser().getUserId())) {
            throw new ForbiddenException("You are not allowed to delete this comment");
        }
        comment.setArticle(null);
        commentRepository.save(comment);
        commentRepository.deleteById(id);
        return null;
    }



    @Override
    public CommentResponse updateCommentById(Long id, CommentRequest commentRequest) {
        Long userId = userService.getCurrentUser().getUserId();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to update this comment");
        }
        comment.setCmt(commentRequest.getComment());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return comment.toResponse();
    }
}
