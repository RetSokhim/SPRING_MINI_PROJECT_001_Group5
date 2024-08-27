package org.example.spring_mini_project.model.response;


import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private Integer status;
    private LocalDateTime timestamp;
}
