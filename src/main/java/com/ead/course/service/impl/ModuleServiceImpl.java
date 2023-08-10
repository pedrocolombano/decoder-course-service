package com.ead.course.service.impl;

import com.ead.commonlib.exception.ResourceNotFoundException;
import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.entity.Course;
import com.ead.course.entity.Lesson;
import com.ead.course.entity.Module;
import com.ead.course.mapper.ModuleMapper;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseFetchService;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleFetchService;
import com.ead.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleMapper moduleMapper;

    private final ModuleFetchService moduleFetchService;
    private final CourseFetchService courseFetchService;
    private final LessonService lessonService;

    private final ModuleRepository moduleRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Module> findAllByCourseId(final Specification<Module> moduleSpecification,
                                          final Pageable pageable) {
        return moduleRepository.findAll(moduleSpecification, pageable);
    }

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

    @Override
    @Transactional
    public Module insert(final UUID courseId, final ModuleInsertDTO moduleInsertDTO) {
        final Course course = courseFetchService.findById(courseId);
        final Module moduleToInsert = moduleMapper.fromModuleInsertDto(moduleInsertDTO);
        moduleToInsert.setCourse(course);

        return moduleRepository.save(moduleToInsert);
    }

    @Override
    @Transactional
    public void deleteAllByCourseId(final UUID courseId) {
        final List<Module> modules = moduleRepository.findAllByCourseId(courseId);
        deleteAll(modules);
    }

    @Override
    @Transactional
    public Module update(final UUID moduleId, final UUID courseId, final ModuleInsertDTO moduleInsertDTO) {
        final Module module = findByModuleIdAndCourseId(moduleId, courseId);
        moduleMapper.map(moduleInsertDTO, module);
        return moduleRepository.save(module);
    }

    @Override
    @Transactional(readOnly = true)
    public Module findByModuleIdAndCourseId(final UUID moduleId, final UUID courseId) {
        return moduleRepository.findByModuleIdAndCourseId(moduleId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Module findById(final UUID moduleId) {
        return moduleFetchService.findById(moduleId);
    }
}
