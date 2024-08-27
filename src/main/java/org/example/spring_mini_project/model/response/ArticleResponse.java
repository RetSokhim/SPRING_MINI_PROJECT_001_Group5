package org.example.spring_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;
    private Long ownerOfArticle;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> categoryList;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentResponse> comments;
}
