package org.example.spring_mini_project.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Must not start with a space")
    private String categoryName;
    public Category toCategory(){
        return new Category(null,this.categoryName.trim().replaceAll("//s+",""), LocalDateTime.now(),null,null,null);
    }
}
