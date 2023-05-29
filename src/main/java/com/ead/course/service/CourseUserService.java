package com.ead.course.service;

import com.ead.course.dto.response.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {

    Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable);

}
