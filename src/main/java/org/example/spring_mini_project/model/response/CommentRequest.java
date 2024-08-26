package org.example.spring_mini_project.model.response;

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
    private String comment;

    public Comment toComment(){
        return new Comment(null,this.comment, LocalDateTime.now(),null,null,null);
    }
}
