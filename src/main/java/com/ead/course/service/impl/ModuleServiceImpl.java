package com.ead.course.service.impl;

import com.ead.course.entity.Lesson;
import com.ead.course.entity.Module;
import com.ead.course.exception.ResourceNotFoundException;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final LessonService lessonService;
    private final ModuleRepository moduleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Module> findAllByCourseId(final UUID courseId) {
        return moduleRepository.findAllByCourseId(courseId);
    }

    @Override
    @Transactional
    public void deleteAll(final List<Module> modules) {
        modules.forEach(this::deleteModuleLessons);
        moduleRepository.deleteAll(modules);
    }

    private void deleteModuleLessons(final Module module) {
        final List<Lesson> moduleLessons = lessonService.findAllByModuleId(module.getModuleId());
        lessonService.deleteAll(moduleLessons);
    }

    @Override
    @Transactional
    public void deleteById(final UUID moduleId) {
        try {
            final Module module = moduleRepository.getById(moduleId);
            deleteModuleLessons(module);
            moduleRepository.delete(module);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Module not found.");
        }
    }

}
