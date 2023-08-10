package com.ead.course.service.impl;

import com.ead.commonlib.exception.InvalidSubscriptionException;
import com.ead.course.dto.request.CourseSubscriptionDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.Course;
import com.ead.course.entity.CourseUser;
import com.ead.course.enumerated.UserStatus;
import com.ead.course.proxy.UserProxy;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseFetchService;
import com.ead.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final UserProxy userProxy;

    private final CourseFetchService courseFetchService;
    private final CourseUserRepository courseUserRepository;

    @Override
    public Page<UserDTO> getAllUsersByCourse(final UUID courseId, final Pageable pageable) {
        return userProxy.getAllUsersByCourse(courseId, pageable);
    }

    @Override
    @Transactional
    public CourseUser subscribeUserIntoCourse(final UUID courseId, final CourseSubscriptionDTO subscriptionDTO) {
        log.info("Starting user {} subscription into course {}.", subscriptionDTO.getUserId(), courseId);

        final Course course = courseFetchService.findById(courseId);
        validateIfUserIsAlreadySubscribedIntoCourse(course, subscriptionDTO);
        validateUser(subscriptionDTO);

        userProxy.subscribeUserIntoCourse(subscriptionDTO.getUserId(), courseId);

        final CourseUser userToSubscribe = buildCourseUser(course, subscriptionDTO.getUserId());
        return courseUserRepository.save(userToSubscribe);
    }

    private void validateIfUserIsAlreadySubscribedIntoCourse(final Course course, final CourseSubscriptionDTO subscriptionDTO) {
        boolean isUserAlreadySubscribed = courseUserRepository.existsByCourseAndUserId(course, subscriptionDTO.getUserId());
        if (isUserAlreadySubscribed) {
            log.info("The user {} is already subscribed into the course {}.", subscriptionDTO.getUserId(), course.getName());
            throw new InvalidSubscriptionException("The user is already subscribed into this course.");
        }
    }

    private void validateUser(final CourseSubscriptionDTO subscriptionDTO) {
        final UserDTO user = userProxy.findById(subscriptionDTO.getUserId());
        validateIfUserIsNotBlocked(user);
    }

    private void validateIfUserIsNotBlocked(final UserDTO userDTO) {
        if (UserStatus.BLOCKED.equals(userDTO.getUserStatus())) {
            log.info("User {} is blocked and can't be subscribed.", userDTO.getUserId());
            throw new InvalidSubscriptionException("The user is blocked and can't be subscribed into the course.");
        }
    }

    private CourseUser buildCourseUser(final Course course, final UUID userId) {
        return CourseUser.builder()
                .course(course)
                .userId(userId)
                .build();
    }

    @Override
    @Transactional
    public void deleteAllByCourseId(final UUID courseId) {
        courseUserRepository.deleteAllByCourseCourseId(courseId);
    }

}
