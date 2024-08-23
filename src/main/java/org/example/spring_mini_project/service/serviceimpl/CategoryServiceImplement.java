package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.repository.CategoryRepository;
import org.example.spring_mini_project.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImplement(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
