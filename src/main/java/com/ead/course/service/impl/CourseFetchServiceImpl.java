package com.ead.course.service.impl;

import com.ead.commonlib.exception.ResourceNotFoundException;
import com.ead.course.entity.Course;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CourseFetchServiceImpl implements CourseFetchService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public Course findById(final UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found."));
    }

}
