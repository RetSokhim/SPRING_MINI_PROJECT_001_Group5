package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
