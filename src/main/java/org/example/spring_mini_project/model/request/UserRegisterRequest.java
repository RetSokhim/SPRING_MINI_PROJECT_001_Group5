package org.example.spring_mini_project.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NonNull
    @NotBlank(message = "Username is required and cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NonNull
    @NotBlank(message = "Email is required and cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NonNull
    @NotBlank(message = "Address is required and cannot be blank")
    private String address;

    @NonNull
    @NotBlank(message = "Password is required and cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace"
    )
    private String password;

    @NonNull
    @NotBlank(message = "Phone number is required and cannot be blank")
    @Pattern(regexp = "^[0-9]{8}$", message = "Phone number must be exactly 8 digits")
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
