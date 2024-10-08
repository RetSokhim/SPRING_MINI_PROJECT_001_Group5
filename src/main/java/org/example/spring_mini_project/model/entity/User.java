package org.example.spring_mini_project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.enumeration.Role;
import org.example.spring_mini_project.model.response.UserRegisterResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    private Role role;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Category> categories;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Article> articles;

    public UserRegisterResponse toResponse(){
        return new UserRegisterResponse(this.userId,
                this.username,this.email,this.role.name(),this.address,this.phoneNumber,this.createdAt
                );
    }
}
