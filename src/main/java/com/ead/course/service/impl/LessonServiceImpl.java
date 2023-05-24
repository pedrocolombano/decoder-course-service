package com.ead.course.service.impl;

import com.ead.course.entity.Lesson;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Lesson> findAllByModuleId(final UUID moduleId) {
        return lessonRepository.findAllByModule(moduleId);
    }

    @Override
    @Transactional
    public void deleteAll(final List<Lesson> lessons) {
        lessonRepository.deleteAll(lessons);
    }
}
