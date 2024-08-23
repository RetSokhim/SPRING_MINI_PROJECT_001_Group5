package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.repository.ArticleRepository;
import org.example.spring_mini_project.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImplement implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImplement(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
}
