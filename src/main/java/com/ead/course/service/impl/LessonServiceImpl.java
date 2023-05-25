package com.ead.course.service.impl;

import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.entity.Lesson;
import com.ead.course.entity.Module;
import com.ead.course.mapper.LessonMapper;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    @Lazy
    private final ModuleService moduleService;

    private final LessonMapper lessonMapper;
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

    @Override
    @Transactional
    public Lesson insert(final UUID moduleId, final LessonInsertDTO lessonInsertDTO) {
        final Module module = moduleService.findById(moduleId);
        final Lesson newLesson = lessonMapper.fromLessonInsertDto(lessonInsertDTO);
        newLesson.setModule(module);

        return lessonRepository.save(newLesson);
    }


}
