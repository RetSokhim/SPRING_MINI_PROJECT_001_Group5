package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryArticleRepository extends JpaRepository<CategoryArticle,Long> {
    Long countAllByCategoryCategoryId(Long categoryId);

    @Query(value = """
    SELECT category_id FROM category_article WHERE article_id = :articleId
    """,nativeQuery = true)
    List<Long> findCategoryIdByArticleId(Long articleId);

    @Modifying
    @Transactional
    @Query(value = """
    DELETE FROM category_article WHERE article_id = :articleId
    """,nativeQuery = true)
    void deleteByArticleArticleId(Long articleId);
}
