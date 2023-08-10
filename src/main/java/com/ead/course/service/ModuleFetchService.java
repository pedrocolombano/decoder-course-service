package com.ead.course.service;

import com.ead.course.entity.Module;

import java.util.UUID;

public interface ModuleFetchService {

    Module findById(UUID moduleId);

}
