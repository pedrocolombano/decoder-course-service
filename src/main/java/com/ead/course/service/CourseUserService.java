package com.ead.course.service;

import com.ead.course.dto.request.CourseSubscriptionDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.CourseUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {

    Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable);
    CourseUser subscribeUserIntoCourse(UUID courseId, CourseSubscriptionDTO subscriptionDTO);
    void deleteAllByCourseId(UUID courseId);

}
