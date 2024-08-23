package org.example.spring_mini_project.model.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;

    public UserRegisterResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.createdAt = user.getCreatedAt();
    }
}
