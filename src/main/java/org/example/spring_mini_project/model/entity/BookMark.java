package org.example.spring_mini_project.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.spring_mini_project.model.response.BookMarkResponse;
import org.example.spring_mini_project.model.response.CommentResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "bookmark")
@ToString
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;
    @Column(nullable = false)
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
