package com.ead.course.dto.response;

import com.ead.course.entity.enumerated.CourseLevel;
import com.ead.course.entity.enumerated.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDTO implements Serializable {

    private static final long serialVersionUID = -6404209880830180167L;

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
    private UUID courseInstructor;

}
