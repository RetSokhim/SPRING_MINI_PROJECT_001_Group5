package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.ArticleRequest;
import org.example.spring_mini_project.model.request.CommentRequest;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.model.response.ArticleResponse;
import org.example.spring_mini_project.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/article")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create-article")
    public ResponseEntity<?> createNewArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        ArticleResponse articleResponse = articleService.createNewCategory(articleRequest);
        return new ResponseEntity<>(new ApiResponse<>("Article Created Successfully",
                HttpStatus.CREATED,articleResponse,201, LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @GetMapping("/get-article-id/{articleId}")
    public ResponseEntity<?> getArticleById(@PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.getArticleById(articleId);
        return new ResponseEntity<>(new ApiResponse<>("Get article with ID : "+articleId+" Successfully",
                HttpStatus.OK,articleResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/get-all-article")
    public ResponseEntity<?> getAllArticle(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String sortBy,
                                           @RequestParam SortDirection sortDirection
    ) {
        List<ArticleResponse> articleResponses = articleService.getAllArticle(pageNumber,pageSize,sortBy,sortDirection);
        return new ResponseEntity<>(new ApiResponse<>("Get all article successfully",
                HttpStatus.OK,articleResponses,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/update-article/{articleId}")
    public ResponseEntity<?> updateArticleById(@RequestBody ArticleRequest articleRequest,@PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.updateArticleById(articleRequest,articleId);
        return new ResponseEntity<>(new ApiResponse<>("Updated article with ID : "+articleId+" Successfully",
                HttpStatus.OK,articleResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/delete-article/{articleId}")
    public ResponseEntity<?> deleteArticleById(@PathVariable Long articleId) {
        articleService.deleteArticleById(articleId);
        return new ResponseEntity<>(new ApiResponse<>("Deleted article with ID : "+articleId+" Successfully",
                HttpStatus.OK,null,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/get-comment/{articleId}")
    public ResponseEntity<?> getCommentInArticle(@PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.getArticleById(articleId);
        return new ResponseEntity<>(new ApiResponse<>("Get all comment on article with ID : "+articleId+" Successfully",
                HttpStatus.OK,articleResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PostMapping("post-comment/{articleId}")
    public ResponseEntity<?> postCommentInArticle(@PathVariable Long articleId, @RequestBody CommentRequest commentRequest){
        ArticleResponse articleResponse = articleService.postCommentToArticle(articleId,commentRequest);
        return new ResponseEntity<>(new ApiResponse<>("Add comment on article with ID : "+articleId+" Successfully",
                HttpStatus.OK,articleResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }
}
