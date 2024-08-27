package org.example.spring_mini_project.service;

import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserRegisterResponse createNewUser(UserRegisterRequest userRegisterRequest, Role role);

    UserRegisterResponse getCurrentUser();

    UserRegisterResponse updateCurrentUser(UserRegisterRequest userRegisterRequest, Role role);
}
