package com.ead.course.service;

import com.ead.course.entity.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonService {

    List<Lesson> findAllByModuleId(UUID moduleId);
    void deleteAll(List<Lesson> lessons);

}
