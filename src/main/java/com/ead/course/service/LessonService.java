package com.ead.course.service;

import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    Page<Lesson> findAllByModuleId(Specification<Lesson> lessonSpecification, Pageable pageable);
    List<Lesson> findAllByModuleId(UUID moduleId);
    void deleteAll(List<Lesson> lessons);
    Lesson insert(UUID moduleId, LessonInsertDTO lessonInsertDTO);
    void deleteByIdAndModuleId(UUID lessonId, UUID moduleId);
    Lesson findByLessonIdAndModuleId(UUID lessonId, UUID moduleId);
    Lesson update(UUID lessonId, UUID moduleId, LessonInsertDTO lessonDto);

}
