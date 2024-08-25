package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByUserEmailAndCategoryId(String email,Long categoryId);
    Page<Category> findAllByUserEmail(Pageable pageable,String email);
    Optional<Category> findCategoryByCategoryIdAndUserEmail(Long categoryId, String name);
    @Modifying
    @Transactional
    @Query("DELETE FROM category c WHERE c.user.email = :email AND c.categoryId = :categoryId")
    void deleteCategoryByUserEmailAndCategoryId(@Param("email") String email, @Param("categoryId") Long categoryId);

}
