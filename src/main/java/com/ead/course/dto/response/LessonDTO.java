package com.ead.course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonDTO implements Serializable {

    private static final long serialVersionUID = 8828140735182655513L;

    private UUID lessonId;
    private String title;
    private String description;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
