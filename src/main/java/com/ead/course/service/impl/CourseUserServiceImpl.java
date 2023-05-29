package com.ead.course.service.impl;

import com.ead.course.dto.response.UserDTO;
import com.ead.course.proxy.UserProxy;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final UserProxy userProxy;
    private final CourseUserRepository courseUserRepository;

    @Override
    public Page<UserDTO> getAllUsersByCourse(final UUID courseId, final Pageable pageable) {
        return userProxy.getAllUsersByCourse(courseId, pageable);
    }
}
