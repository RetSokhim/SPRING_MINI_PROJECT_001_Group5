package org.example.spring_mini_project.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private String fileName;
    private String fileType;
    private Long fileSize;
}
