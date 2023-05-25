package com.ead.course.service;

import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.entity.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    Page<Module> findAllByCourseId(Specification<Module> moduleSpecification, Pageable pageable);
    List<Module> findAllByCourseId(UUID courseId);
    void deleteAll(List<Module> modules);
    void deleteById(UUID moduleId);
    Module insert(UUID courseId, ModuleInsertDTO moduleInsertDTO);
    void deleteAllByCourseId(UUID courseId);
    Module update(UUID moduleId, UUID courseId, ModuleInsertDTO moduleInsertDTO);
    Module findByModuleIdAndCourseId(UUID moduleId, UUID courseId);
    Module findById(UUID moduleId);

}
