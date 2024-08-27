package org.example.spring_mini_project.model.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.spring_mini_project.model.entity.Comment;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    @NotNull(message = "Must be not null")
    @NotBlank(message = "Must be not blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Must not start with a space")
    private String comment;
    public Comment toComment(){
        return new Comment(null,this.comment.trim().replaceAll("\\s+"," "), LocalDateTime.now(),null,null,null);
    }
}
