package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.enumeration.SortCategory;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.request.CategoryRequest;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.model.response.CategoryResponse;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.example.spring_mini_project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create-category")
    public ResponseEntity<?> createNewCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createNewCategory(categoryRequest);
        return new ResponseEntity<>(new ApiResponse<>("Category Created Successfully",
                HttpStatus.CREATED,categoryResponse,201, LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @GetMapping("/get-category-id/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse<>("Get category with ID : "+categoryId+" Successfully",
                HttpStatus.OK,categoryResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "1") Integer pageNumber,
                                              @RequestParam(defaultValue = "5") Integer pageSize,
                                              @RequestParam SortCategory sortBy,
                                              @RequestParam SortDirection sortDirection
                                              ) {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortDirection);
        return new ResponseEntity<>(new ApiResponse<>("Get all category successfully",
                HttpStatus.OK,categoryResponses,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @PutMapping("/update-category/{categoryId}")
    public ResponseEntity<?> updateCategoryById(@Valid @RequestBody CategoryRequest categoryRequest,@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.updateCategoryById(categoryId,categoryRequest);
        return new ResponseEntity<>(new ApiResponse<>("Updated category with ID : "+categoryId+" Successfully",
                HttpStatus.OK,categoryResponse,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse<>("Deleted category with ID : "+categoryId+" Successfully",
                HttpStatus.OK,null,200, LocalDateTime.now()
        ), HttpStatus.OK);
    }
}
