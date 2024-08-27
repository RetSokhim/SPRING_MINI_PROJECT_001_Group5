package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findBookMarksByArticle_ArticleId(Long articleId);

    Optional<BookMark> findByArticle_ArticleIdAndUser_UserId(Long articleId, Long userId);
}

