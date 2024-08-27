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
@Entity(name = "article")
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER)
    private List<CategoryArticle> categoryArticles;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER)
    private List<BookMark> bookMark;
    @OneToMany(mappedBy = "article",fetch = FetchType.EAGER)
    private List<Comment> comment;

    public BookMarkResponse toResponse(){
        BookMarkResponse bookMarkResponse = new BookMarkResponse();
        bookMarkResponse.setArticleId(articleId);
        bookMarkResponse.setTitle(title);
        bookMarkResponse.setCreatedAt(createdAt);
        bookMarkResponse.setDescription(description);
        bookMarkResponse.setOwnerOfArticle(user.getUserId());
        //Category id
        List<Long> categoryIdList = new ArrayList<>();
        for(CategoryArticle categoryArticle : categoryArticles){
            categoryIdList.add(categoryArticle.getId());
            bookMarkResponse.setCategoryIdList(categoryIdList);
        }
        // comment
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for(Comment comment : comment){
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(comment.getCommentId());
            commentResponse.setCmt(comment.getCmt());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setUpdatedAt(comment.getUpdatedAt());
            //user comment
            commentResponse.setUser(comment.toResponse().getUser());
            commentResponseList.add(commentResponse);
            bookMarkResponse.setCommentList(commentResponseList);
        }
        return bookMarkResponse;

    }


}
