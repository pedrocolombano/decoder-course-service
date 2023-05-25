package com.ead.course.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonInsertDTO implements Serializable {

    private static final long serialVersionUID = -8327445358428721771L;

    private String title;
    private String description;
    private String videoUrl;

}
