package com.ead.course.service.impl;

import com.ead.course.dto.request.CourseSubscriptionDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.Course;
import com.ead.course.entity.CourseUser;
import com.ead.course.proxy.UserProxy;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.service.InvalidSubscriptionException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final UserProxy userProxy;

    private final CourseService courseService;
    private final CourseUserRepository courseUserRepository;

    @Override
    public Page<UserDTO> getAllUsersByCourse(final UUID courseId, final Pageable pageable) {
        return userProxy.getAllUsersByCourse(courseId, pageable);
    }

    @Override
    @Transactional
    //TODO: validate if user exists in AuthUser Service
    public CourseUser subscribeUserIntoCourse(final UUID courseId, final CourseSubscriptionDTO subscriptionDTO) {
        final Course course = courseService.findById(courseId);
        validateIfUserIsAlreadySubscribedIntoCourse(course, subscriptionDTO);

        final CourseUser userToSubscribe = buildCourseUser(course, subscriptionDTO.getUserId());
        return courseUserRepository.save(userToSubscribe);
    }

    private void validateIfUserIsAlreadySubscribedIntoCourse(final Course course, final CourseSubscriptionDTO subscriptionDTO) {
        boolean isUserAlreadySubscribed = courseUserRepository.existsByCourseAndUserId(course, subscriptionDTO.getUserId());
        if (isUserAlreadySubscribed) {
            throw new InvalidSubscriptionException("The user is already subscribed into this course.");
        }
    }

    private CourseUser buildCourseUser(final Course course, final UUID userId) {
        return CourseUser.builder()
                .course(course)
                .userId(userId)
                .build();
    }

}
