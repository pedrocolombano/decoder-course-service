package com.ead.course.service;

import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.entity.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    List<Module> findAllByCourseId(UUID courseId);
    void deleteAll(List<Module> modules);
    void deleteById(UUID moduleId);
    Module insert(UUID courseId, ModuleInsertDTO moduleInsertDTO);
    void deleteAllByCourseId(UUID courseId);
    Module update(UUID moduleId, UUID courseId, ModuleInsertDTO moduleInsertDTO);
    Module findByModuleIdAndCourseId(UUID moduleId, UUID courseId);

}
