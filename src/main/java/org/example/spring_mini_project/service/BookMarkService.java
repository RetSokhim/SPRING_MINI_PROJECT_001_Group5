package org.example.spring_mini_project.service;
import org.example.spring_mini_project.model.entity.Article;
import org.example.spring_mini_project.model.enumeration.SortArticle;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.response.BookMarkResponse;

import java.util.List;

public interface BookMarkService {
    Object markArticleToBookMark(Long articleId);

    Object unMarkedArticleFromBookMark(Long articleId);

    List<BookMarkResponse> getAllBookMark(Integer pageNo, Integer pageSize, SortArticle sortBy, SortDirection sortDirection);
}
