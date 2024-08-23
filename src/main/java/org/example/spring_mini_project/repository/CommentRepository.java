package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
