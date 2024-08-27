package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.spring_mini_project.model.enumeration.SortArticle;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.service.BookMarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
public class BookMarkController {
    private final BookMarkService bookMarkService;
    public BookMarkController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookMark(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize, @RequestParam SortArticle sortBy, @RequestParam SortDirection sortDirection) {
        ApiResponse<?> response = new ApiResponse<>(
                "Get all bookmarked article successfully",
                HttpStatus.OK,
                bookMarkService.getAllBookMark(pageNo,pageSize,sortBy,sortDirection),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/{articleId}")
    public ResponseEntity<?> markArticleToBookMark( @PathVariable Long articleId) {
        ApiResponse<?> response = new ApiResponse<>(
                "Add article id "+articleId+" to bookmark successfully",
                HttpStatus.OK,
                bookMarkService.markArticleToBookMark(articleId),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/{articleId}")
    public ResponseEntity<?> unMarkedArticleFromBookMark(@PathVariable Long articleId) {
        ApiResponse<?> response = new ApiResponse<>(
                "Unmarked article id "+articleId+" from bookmark successfully",
                HttpStatus.OK,
                bookMarkService.unMarkedArticleFromBookMark(articleId),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
