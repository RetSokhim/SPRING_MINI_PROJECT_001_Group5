package org.example.spring_mini_project.service.serviceimpl;

import jakarta.transaction.Transactional;
import org.example.spring_mini_project.exception.ConflictException;
import org.example.spring_mini_project.exception.NotFoundException;
import org.example.spring_mini_project.exception.PasswordException;
import org.example.spring_mini_project.model.entity.CustomUserDetail;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.request.UserRegisterRequest;
import org.example.spring_mini_project.model.response.UserRegisterResponse;
import org.example.spring_mini_project.repository.UserRepository;
import org.example.spring_mini_project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImplement(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        return new CustomUserDetail(user);
    }

    @Override
    public UserRegisterResponse createNewUser(UserRegisterRequest userRegisterRequest, Role role) {
        User user = userRegisterRequest.toUser(role);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            throw new ConflictException("Email already exists");
        }
        userRepository.save(user);
        return new UserRegisterResponse(user);
    }
    @Override
    @Transactional
    public UserRegisterResponse updateCurrentUser(UserRegisterRequest userRegisterRequest, Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findUserByEmail(username);

                // Update the user's details
                user.setUsername(userRegisterRequest.getUsername());
                user.setEmail(userRegisterRequest.getEmail());
                user.setAddress(userRegisterRequest.getAddress());
                user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
                user.setUpdatedAt(LocalDateTime.now());
                // Update the role if it's provided and different
                if (!user.getRole().equals(role)) {
                    user.setRole(role);
                }

                // If the password is provided and not empty, update it
                if (userRegisterRequest.getPassword() != null && !userRegisterRequest.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
                }

                // Save the updated user to the database
                userRepository.save(user);

                // Return the updated user details
                return new UserRegisterResponse(user);
            }
        }

        throw new UsernameNotFoundException("User not found or not authenticated");
    }

    @Override
    public User getUserByEmail(String email) throws PasswordException {
        User user = userRepository.findUserByEmail(email);
        if(user == null){
            throw new PasswordException("No user with this email");
        }
        return user;
    }

    @Override
    public UserRegisterResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userRepository.findUserByEmail(username);

                if (user != null) {
                    return new UserRegisterResponse(user);
                } else {
                    throw new NotFoundException("User not found in the database");
                }
            } else {
                throw new NotFoundException("Principal is not an instance of UserDetails");
            }
        } else {
            throw new NotFoundException("User not authenticated");
        }
    }
}
