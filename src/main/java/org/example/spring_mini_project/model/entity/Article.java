package org.example.spring_mini_project.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.response.ArticleResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String title;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<CategoryArticle> categoryArticles;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<BookMark> bookMark;
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<Comment> comment;

    public ArticleResponse toResponse(){
        return new ArticleResponse(this.articleId,this.title,this.description,this.createdAt,this.updatedAt,this.user.getUserId(),null,null);
    }
}
