package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.model.response.UserDTO;
import org.example.spring_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserInfo() {
        ApiResponse<?> response = new ApiResponse<>(
                "Get user info successfully",
                HttpStatus.OK,
                userService.getUserInfo(),
                HttpStatus.OK.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
