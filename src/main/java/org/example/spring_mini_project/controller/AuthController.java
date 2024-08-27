package org.example.spring_mini_project.controller;

import jakarta.validation.Valid;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.request.UserLoginRequest;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.ApiResponse;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.example.spring_mini_project.security.JwtService;
import org.example.spring_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest,
                                      @RequestParam Role role
    ) {
        UserRegisterResponse authRegister = userService.createNewUser(userRegisterRequest, role);
        return new ResponseEntity<>(new ApiResponse<>("Register Successfully",
                HttpStatus.CREATED, authRegister, 201, LocalDateTime.now()
        ), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) throws Exception {
        authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(userLoginRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        return new ResponseEntity<>(new ApiResponse<>("Login successfully",
                HttpStatus.OK, token, 200, LocalDateTime.now()
        ), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
//            UserDetails user = userService.loadUserByUsername(email);
//            if (!passwordEncoder.matches(password, user.getPassword())) {
//                throw new PasswordException("Your password is incorrect please try again");
//            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
