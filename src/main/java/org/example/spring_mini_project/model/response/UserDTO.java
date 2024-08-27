package org.example.spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.enumeration.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
}
