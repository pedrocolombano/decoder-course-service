package com.ead.course.service.impl;

import com.ead.course.repository.CourseUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl {

    private final CourseUserRepository courseUserRepository;

}
