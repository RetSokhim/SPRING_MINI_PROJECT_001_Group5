package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.model.entity.*;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.ArticleRequest;
import org.example.spring_mini_project.model.response.ArticleResponse;
import org.example.spring_mini_project.model.response.CommentRequest;
import org.example.spring_mini_project.model.response.CommentResponse;
import org.example.spring_mini_project.repository.*;
import org.example.spring_mini_project.service.ArticleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImplement implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryArticleRepository categoryArticleRepository;
    private final CommentRepository commentRepository;

    public ArticleServiceImplement(ArticleRepository articleRepository, UserRepository userRepository,
                                   CategoryRepository categoryRepository, CategoryArticleRepository categoryArticleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.categoryArticleRepository = categoryArticleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ArticleResponse createNewCategory(ArticleRequest articleRequest) {
        Article article = articleRequest.toArticle();
        User user = userRepository.findUserByEmail(getCurrentUserEmail());
        article.setUser(user);
        articleRepository.save(article);
        List<CategoryArticle> categoryArticles = articleRequest.getCategoryId().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    CategoryArticle categoryArticle = new CategoryArticle();
                    categoryArticle.setArticle(article);
                    categoryArticle.setCategory(category);
                    categoryArticle.setCreatedAt(LocalDateTime.now());
                    return categoryArticle;
                })
                .collect(Collectors.toList());
        categoryArticleRepository.saveAll(categoryArticles);
        ArticleResponse articleResponse = article.toResponse();
        articleResponse.setCategoryList(
                categoryArticles.stream()
                        .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                        .collect(Collectors.toList())
        );
        return articleResponse;
    }

    @Override
    public ArticleResponse getArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        ArticleResponse articleResponse = article.toResponse();
        articleResponse.setCategoryList(categoryArticleRepository.findCategoryIdByArticleId(articleId));
        List<CommentResponse> commentResponses = commentRepository.findCommentsByArticleArticleId(article.getArticleId())
                .stream().
                map(Comment::toResponse).toList();
        articleResponse.setComments(commentResponses);
        articleResponse.setComments(commentResponses);
        return articleResponse;
    }

    @Override
    public List<ArticleResponse> getAllArticle(Integer pageNumber, Integer pageSize, String sortBy, SortDirection sortDirection) {
        Sort sort = sortDirection.name().equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        List<Article> articles = articleRepository.findAll(pageable).getContent();
        return articles.stream().map(article -> {
            ArticleResponse articleResponse = article.toResponse();
            articleResponse.setCategoryList(categoryArticleRepository.findCategoryIdByArticleId(article.getArticleId()));
            List<CommentResponse> commentResponses = commentRepository.findCommentsByArticleArticleId(article.getArticleId())
                    .stream().
                    map(Comment::toResponse).toList();
            articleResponse.setComments(commentResponses);
            return articleResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public ArticleResponse updateArticleById(ArticleRequest articleRequest, Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(article);
        categoryArticleRepository.deleteByArticleArticleId(articleId);
        List<CategoryArticle> categoryArticles = articleRequest.getCategoryId().stream()
                .map(categoryId->{
                    Category category = categoryRepository.findById(categoryId).orElseThrow();
                    CategoryArticle categoryArticle = new CategoryArticle();
                    categoryArticle.setCategory(category);
                    categoryArticle.setArticle(article);
                    categoryArticle.setUpdatedAt(LocalDateTime.now());
                    categoryArticle.setCreatedAt(article.getCreatedAt());
                    categoryArticleRepository.save(categoryArticle);
                    return categoryArticle;
                }).toList();
        ArticleResponse articleResponse = article.toResponse();
        articleResponse.setCategoryList(categoryArticles.stream()
                .map(categoryArticle -> categoryArticle.getCategory()
                        .getCategoryId())
                .collect(Collectors.toList()));
        articleResponse.setComments(commentRepository.findCommentsByArticleArticleId(article.getArticleId())
                .stream().map(Comment::toResponse).toList());
        return articleResponse;
    }

    @Override
    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Override
    public ArticleResponse postCommentToArticle(Long articleId, CommentRequest commentRequest) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        System.out.println("Hello 1");
        Comment comment = commentRequest.toComment();
        comment.setArticle(article);
        comment.setUser(userRepository.findUserByEmail(getCurrentUserEmail()));
        System.out.println("Hello 2");
        commentRepository.save(comment);
        CommentResponse commentResponse = comment.toResponse();
        List<CommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        ArticleResponse articleResponse = article.toResponse();
        articleResponse.setComments(commentResponses);
        return articleResponse;
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
