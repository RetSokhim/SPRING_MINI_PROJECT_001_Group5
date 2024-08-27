package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
