package org.example.spring_mini_project.model.request;

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
public class UserRegisterRequest {
    private String username;
    private String email;
    private String address;
    private String password;
    private String phoneNumber;

    public User toUser(Role role){
        return new User(null,
                this.username,
                this.email,
                role,
                null,
                this.address,
                this.phoneNumber,
                LocalDateTime.now(),
                null,
                null,
                null,
                null
                );
    }
}
