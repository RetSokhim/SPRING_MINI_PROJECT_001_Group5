package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.model.entity.Category;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.CategoryRequest;
import org.example.spring_mini_project.model.response.CategoryResponse;
import org.example.spring_mini_project.repository.ArticleRepository;
import org.example.spring_mini_project.repository.CategoryArticleRepository;
import org.example.spring_mini_project.repository.CategoryRepository;
import org.example.spring_mini_project.repository.UserRepository;
import org.example.spring_mini_project.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    public CategoryServiceImplement(CategoryRepository categoryRepository, UserRepository userRepository, CategoryArticleRepository categoryArticleRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryArticleRepository = categoryArticleRepository;
    }

    @Override
    public CategoryResponse createNewCategory(CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(authentication.getName());
        Category category = categoryRequest.toCategory();
        category.setUser(user);
        categoryRepository.save(category);
        CategoryResponse categoryResponse = category.toResponse();
        categoryResponse.setAmountOfArticle(0L);
        return categoryResponse;
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Category category = categoryRepository.findCategoryByUserEmailAndCategoryId(authentication.getName(),categoryId);
        CategoryResponse categoryResponse = category.toResponse();
        categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(categoryId));
        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, SortDirection sortDirection) {
        Sort sort = sortDirection.name().equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return categoryRepository.findAllByUserEmail(pageable,authentication.getName())
                .getContent().stream().map(category -> {
                    CategoryResponse categoryResponse = category.toResponse();
                    categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(category.getCategoryId()));
                    return categoryResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Category category = categoryRepository.findCategoryByCategoryIdAndUserEmail(categoryId,authentication.getName()).orElseThrow();
        category.setUpdatedAt(LocalDateTime.now());
        category.setCategoryName(categoryRequest.getCategoryName());
        CategoryResponse categoryResponse = categoryRepository.save(category).toResponse();
        categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(categoryId));
        return categoryResponse;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        categoryRepository.deleteCategoryByUserEmailAndCategoryId(authentication.getName(),categoryId);
    }
}
