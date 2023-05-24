package com.ead.course.service.impl;

import com.ead.course.entity.Module;
import com.ead.course.exception.ResourceNotFoundException;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final ModuleService moduleService;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public void delete(final UUID courseId) {
        final List<Module> courseModules = moduleService.findAllByCourseId(courseId);
        moduleService.deleteAll(courseModules);

        try {
            courseRepository.deleteById(courseId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Course not found.");
        }
    }
}
