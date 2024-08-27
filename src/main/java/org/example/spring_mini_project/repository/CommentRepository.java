package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.Comment;
import org.example.spring_mini_project.model.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentsByArticleArticleId(Long articleId);
}
