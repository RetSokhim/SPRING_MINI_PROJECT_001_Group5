package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.model.entity.CustomUserDetail;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.example.spring_mini_project.repository.UserRepository;
import org.example.spring_mini_project.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImplement(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        return new CustomUserDetail(user);
    }

    @Override
    public UserRegisterResponse createNewUser(UserRegisterRequest userRegisterRequest, Role role) {
        User user = userRegisterRequest.toUser(role);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);
        return new UserRegisterResponse(user);
    }
}
