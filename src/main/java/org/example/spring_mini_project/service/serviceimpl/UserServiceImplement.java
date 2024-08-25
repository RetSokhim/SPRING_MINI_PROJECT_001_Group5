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

    @Override
    public UserRegisterResponse updateCurrentUser(UserRegisterRequest userRegisterRequest) {
        // Find the existing user by email
        User existingUser = userRepository.findUserByEmail(userRegisterRequest.getEmail());

        // Handle the case where the user is not found
        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found with email: " + userRegisterRequest.getEmail());
        }

        // Convert the request to a User object with the provided role
        // Assuming you pass the existing user's role, or decide on the role based on some logic
        User updatedUser = userRegisterRequest.toUser(existingUser.getRole());

        // Update only the necessary fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // Update the password if it's provided
        if (userRegisterRequest.getPassword() != null && !userRegisterRequest.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        }

        // Save the updated user back to the repository
        userRepository.save(existingUser);

        // Return the response with the updated user data
        return new UserRegisterResponse(existingUser);
    }

    @Override
    public UserRegisterResponse getCurrentUser() {

        return null;
    }

}
