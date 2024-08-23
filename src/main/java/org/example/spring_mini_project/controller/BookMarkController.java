package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.spring_mini_project.service.BookMarkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
public class BookMarkController {
    private final BookMarkService bookMarkService;

    public BookMarkController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }
}
