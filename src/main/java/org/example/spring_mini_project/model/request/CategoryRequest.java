package org.example.spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.entity.Category;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private String categoryName;

    public Category toCategory(){
        return new Category(null,this.categoryName, LocalDateTime.now(),null,null,null);
    }
}
