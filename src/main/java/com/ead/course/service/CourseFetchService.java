package com.ead.course.service;

import com.ead.course.entity.Course;

import java.util.UUID;

public interface CourseFetchService {

    Course findById(UUID courseId);

}
