package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.model.entity.Category;
import org.example.spring_mini_project.model.entity.Comment;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.CategoryRequest;
import org.example.spring_mini_project.model.response.ArticleResponse;
import org.example.spring_mini_project.model.response.CategoryResponse;
import org.example.spring_mini_project.model.response.CommentResponse;
import org.example.spring_mini_project.repository.*;
import org.example.spring_mini_project.service.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImplement implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    public CategoryServiceImplement(CategoryRepository categoryRepository, UserRepository userRepository, CategoryArticleRepository categoryArticleRepository, ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryArticleRepository = categoryArticleRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CategoryResponse createNewCategory(CategoryRequest categoryRequest) {
        User user = userRepository.findUserByEmail(getUserCurrentEmail());
        Category category = categoryRequest.toCategory();
        category.setUser(user);
        categoryRepository.save(category);
        CategoryResponse categoryResponse = category.toResponse();
        categoryResponse.setAmountOfArticle(0L);
        return categoryResponse;
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findCategoryByUserEmailAndCategoryId(getUserCurrentEmail(),categoryId);
        CategoryResponse categoryResponse = category.toResponse();
        categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(categoryId));
        List<ArticleResponse> articleResponses = toArticleResponses();
        categoryResponse.setArticleList(articleResponses);
        return categoryResponse;
    }

    private List<ArticleResponse> toArticleResponses() {
        return articleRepository.findAll()
                .stream().map(article -> {
                    ArticleResponse articleResponse = article.toResponse();
                    List<CommentResponse> commentResponses = commentRepository.findCommentsByArticleArticleId(article.getArticleId())
                                    .stream().map(Comment::toResponse).toList();
                    articleResponse.setComments(commentResponses);
                    return articleResponse;
                }).toList();
    }

    @Override
    public List<CategoryResponse> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, SortDirection sortDirection) {
        Sort sort = sortDirection.name().equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);

        return categoryRepository.findAllByUserEmail(pageable,getUserCurrentEmail())
                .getContent().stream().map(category -> {
                    toArticleResponses();
                    CategoryResponse categoryResponse = category.toResponse();
                    categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(category.getCategoryId()));
                    categoryResponse.setArticleList(toArticleResponses());
                    return categoryResponse;
                }).toList();
    }

    @Override
    public CategoryResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findCategoryByCategoryIdAndUserEmail(categoryId,getUserCurrentEmail()).orElseThrow();
        category.setUpdatedAt(LocalDateTime.now());
        category.setCategoryName(categoryRequest.getCategoryName());
        CategoryResponse categoryResponse = categoryRepository.save(category).toResponse();
        categoryResponse.setAmountOfArticle(categoryArticleRepository.countAllByCategoryCategoryId(categoryId));
        return categoryResponse;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteCategoryByUserEmailAndCategoryId(getUserCurrentEmail(),categoryId);
    }

    public String getUserCurrentEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
