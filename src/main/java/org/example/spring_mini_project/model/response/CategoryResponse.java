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
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;
    private Long amountOfArticle;
    private LocalDateTime createdAt;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArticleResponse> articleList;
}
