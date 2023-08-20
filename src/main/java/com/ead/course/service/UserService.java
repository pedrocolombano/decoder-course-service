package com.ead.course.service;

import com.ead.course.entity.User;
import com.ead.course.specification.UserSpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    Page<User> findAllUsersByCourseId(UUID courseId, UserSpecificationTemplate.UserSpecification specification, Pageable pageable);
}
