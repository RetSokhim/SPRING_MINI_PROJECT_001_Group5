package org.example.spring_mini_project.service;
import org.example.spring_mini_project.model.enumeration.SortArticle;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.response.ArticleResponse;

import java.util.List;

public interface BookMarkService {
    Object markArticleToBookMark(Long articleId);

    Object unMarkedArticleFromBookMark(Long articleId);

    List<ArticleResponse> getAllBookMark(Integer pageNo, Integer pageSize, SortArticle sortBy, SortDirection sortDirection);
}
