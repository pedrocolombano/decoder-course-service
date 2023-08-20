package com.ead.course.service;

import com.ead.course.dto.request.CourseInsertDTO;
import com.ead.course.entity.Course;
import com.ead.course.specification.CourseSpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {

    Page<Course> findAll(CourseSpecificationTemplate.CourseSpecification specification, Pageable pageable);
    Course findById(UUID courseId);
    void delete(UUID courseId);
    Course insert(CourseInsertDTO courseInsertDTO);
    Course update(UUID courseId, CourseInsertDTO course);

}
