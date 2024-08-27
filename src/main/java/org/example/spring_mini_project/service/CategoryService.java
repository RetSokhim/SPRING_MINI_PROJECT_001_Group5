package org.example.spring_mini_project.service;

import org.example.spring_mini_project.model.enumeration.SortCategory;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.CategoryRequest;
import org.example.spring_mini_project.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createNewCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> getAllCategories(Integer pageNumber, Integer pageSize, SortCategory sortBy, SortDirection sortDirection);
    CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategoryById(Long categoryId);
}
