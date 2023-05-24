package com.ead.course.service;

import com.ead.course.entity.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    List<Module> findAllByCourseId(UUID courseId);
    void deleteAll(List<Module> modules);
    void deleteById(UUID moduleId);

}
