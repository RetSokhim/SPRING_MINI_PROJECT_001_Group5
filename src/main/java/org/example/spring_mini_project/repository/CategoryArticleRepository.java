package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryArticleRepository extends JpaRepository<CategoryArticle,Long> {
    Long countAllByCategoryCategoryId(Long categoryId);
}
