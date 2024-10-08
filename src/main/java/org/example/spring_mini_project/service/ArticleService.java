package org.example.spring_mini_project.service;

import org.example.spring_mini_project.model.enumeration.SortArticle;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.ArticleRequest;
import org.example.spring_mini_project.model.request.CommentRequest;
import org.example.spring_mini_project.model.response.ArticleResponse;

import java.util.List;

public interface ArticleService {
    ArticleResponse createNewCategory(ArticleRequest articleRequest);
    ArticleResponse getArticleById(Long articleId);
    List<ArticleResponse> getAllArticle(Integer pageNumber, Integer pageSize, SortArticle sortBy, SortDirection sortDirection);
    ArticleResponse updateArticleById(ArticleRequest articleRequest, Long articleId);
    void deleteArticleById(Long articleId);
    ArticleResponse postCommentToArticle(Long articleId, CommentRequest commentRequest);
}
