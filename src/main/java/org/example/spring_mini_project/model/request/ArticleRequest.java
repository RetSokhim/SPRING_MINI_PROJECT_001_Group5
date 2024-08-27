package org.example.spring_mini_project.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.entity.Article;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Must not start with a space")
    private String title;
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Must not start with a space")
    private String description;
    @NotEmpty(message = "Category id must be not empty")
    private List<Long> categoryId;

    public Article toArticle(){
        return new Article(null,this.title.trim().replaceAll("\\s+"," "),this.description.trim().replaceAll("//s+",""), LocalDateTime.now(),null,null,null,null,null);
    }
}
