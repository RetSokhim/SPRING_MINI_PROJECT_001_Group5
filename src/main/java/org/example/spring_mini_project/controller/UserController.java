package org.example.spring_mini_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @PutMapping
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse userRegisterResponse=userService.updateCurrentUser(userRegisterRequest);
        return new ResponseEntity<>(new ApiResponse<>("update current user successfully",
                HttpStatus.CREATED,userRegisterResponse,201, LocalDateTime.now()
        ), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getCurrentUser() {
        UserRegisterResponse userRegisterResponse=userService.getCurrentUser();
        return new ResponseEntity<>(new ApiResponse<>("get current user successfully",
                HttpStatus.CREATED,
                userRegisterResponse,
                201,
                LocalDateTime.now()
        ),
                HttpStatus.OK
        );
    }
}
