package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.spring_mini_project.service.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/article")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
}
