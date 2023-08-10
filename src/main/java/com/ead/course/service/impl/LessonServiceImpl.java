package com.ead.course.service.impl;

import com.ead.commonlib.exception.ResourceNotFoundException;
import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.entity.Lesson;
import com.ead.course.entity.Module;
import com.ead.course.mapper.LessonMapper;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonMapper lessonMapper;

    private final ModuleFetchService moduleFetchService;
    private final LessonRepository lessonRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Lesson> findAllByModuleId(final Specification<Lesson> lessonSpecification,
                                          final Pageable pageable) {
        return lessonRepository.findAll(lessonSpecification, pageable);
    }

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
        final Module module = moduleFetchService.findById(moduleId);
        final Lesson newLesson = lessonMapper.fromLessonInsertDto(lessonInsertDTO);
        newLesson.setModule(module);

        return lessonRepository.save(newLesson);
    }

    @Override
    @Transactional
    public void deleteByIdAndModuleId(final UUID lessonId, final UUID moduleId) {
        final Lesson lesson = findByLessonIdAndModuleId(lessonId, moduleId);
        lessonRepository.delete(lesson);
    }

    @Override
    @Transactional(readOnly = true)
    public Lesson findByLessonIdAndModuleId(final UUID lessonId, final UUID moduleId) {
        return lessonRepository.findByLessonIdAndModuleModuleId(lessonId, moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));
    }

    @Override
    @Transactional
    public Lesson update(final UUID lessonId, final UUID moduleId, final LessonInsertDTO lessonDto) {
        final Lesson entity = findByLessonIdAndModuleId(lessonId, moduleId);
        lessonMapper.map(lessonDto, entity);
        return lessonRepository.save(entity);
    }


}
