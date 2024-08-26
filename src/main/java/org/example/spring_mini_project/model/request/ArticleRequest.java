package org.example.spring_mini_project.model.request;

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
    private String title;
    private String description;
    private List<Long> categoryId;

    public Article toArticle(){
        return new Article(null,this.title,this.description, LocalDateTime.now(),null,null,null,null,null);
    }
}
