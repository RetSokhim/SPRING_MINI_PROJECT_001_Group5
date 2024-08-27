package org.example.spring_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
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
public class BookMarkResponse {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Long ownerOfArticle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> categoryIdList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentResponse> commentList;
}
