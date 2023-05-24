package com.ead.course.dto.request;

import com.ead.course.entity.enumerated.CourseLevel;
import com.ead.course.entity.enumerated.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseInsertDTO implements Serializable {

    private static final long serialVersionUID = -4095677124001165245L;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private CourseLevel courseLevel;

    @NotNull
    private UUID courseInstructor;

}
