package com.ead.course.mapper;

import com.ead.course.dto.request.CourseInsertDTO;
import com.ead.course.dto.response.CourseDTO;
import com.ead.course.entity.Course;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseMapper {

    private final ModelMapper mapper;

    public Course fromCourseInsertDTO(final CourseInsertDTO courseInsertDTO) {
        return mapper.map(courseInsertDTO, Course.class);
    }

    public CourseDTO fromEntity(final Course course) {
        return mapper.map(course, CourseDTO.class);
    }

    public void map(final CourseInsertDTO source, final Course destination) {
        mapper.map(source, destination);
    }
}
