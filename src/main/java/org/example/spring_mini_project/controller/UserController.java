package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.example.spring_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PutMapping("/current-user")
    public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest, @RequestParam Role role) {
        UserRegisterResponse userRegisterResponse = userService.updateCurrentUser(userRegisterRequest, role);
        return new ResponseEntity<>(new ApiResponse<>(
                "Update current user successfully",
                HttpStatus.CREATED,
                userRegisterResponse,
                201,
                LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        UserRegisterResponse userRegisterResponse = userService.getCurrentUser();
        return new ResponseEntity<>(new ApiResponse<>(
                "Get current user successfully",
                HttpStatus.OK,
                userRegisterResponse,
                200,
                LocalDateTime.now()
        ), HttpStatus.OK);
    }
}
