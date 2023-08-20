package com.ead.course.service.impl;

import com.ead.commonlib.exception.InvalidSubscriptionException;
import com.ead.commonlib.exception.ResourceNotFoundException;
import com.ead.course.dto.request.CourseInsertDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.Course;
import com.ead.course.entity.Module;
import com.ead.course.enumerated.UserType;
import com.ead.course.feignclients.UserClient;
import com.ead.course.mapper.CourseMapper;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseFetchService;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import com.ead.course.service.ModuleService;
import com.ead.course.specification.CourseSpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserClient userClient;

    private final CourseMapper courseMapper;

    private final CourseFetchService courseFetchService;
    private final ModuleService moduleService;
    private final CourseUserService courseUserService;
    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Course> findAll(final UUID userId, final CourseSpecificationTemplate.CourseSpecification specification, final Pageable pageable) {
        if (Objects.nonNull(userId)) {
            final Specification<Course> coursesByUserSpecification = CourseSpecificationTemplate.coursesByUser(userId).and(specification);
            return courseRepository.findAll(coursesByUserSpecification, pageable);
        }
        return courseRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findById(final UUID courseId) {
        return courseFetchService.findById(courseId);
    }

    @Override
    @Transactional
    public void delete(final UUID courseId) {
        final List<Module> courseModules = moduleService.findAllByCourseId(courseId);
        moduleService.deleteAll(courseModules);
        courseUserService.deleteAllByCourseId(courseId);
        deleteCourseById(courseId);
        userClient.deleteUserCourseSubscriptions(courseId);
    }

    private void deleteCourseById(final UUID courseId) {
        try {
            courseRepository.deleteById(courseId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Course not found.");
        }
    }

    @Override
    @Transactional
    public Course insert(final CourseInsertDTO courseInsertDTO) {
        validateIfUserIsAbleToBeAnInstructor(courseInsertDTO);
        final Course courseToInsert = courseMapper.fromCourseInsertDTO(courseInsertDTO);
        return courseRepository.save(courseToInsert);
    }

    private void validateIfUserIsAbleToBeAnInstructor(final CourseInsertDTO courseInsertDTO) {
        final UserDTO user = userClient.findById(courseInsertDTO.getCourseInstructor());
        if (UserType.STUDENT.equals(user.getUserType())) {
            log.info("User {} is not an instructor or admin.", user.getUserId());
            throw new InvalidSubscriptionException("User must be an instructor or admin in order to be a course instructor.");
        }
    }

    @Override
    @Transactional
    public Course update(final UUID courseId, final CourseInsertDTO courseInsertDTO) {
        try {
            final Course course = courseRepository.getById(courseId);
            courseMapper.map(courseInsertDTO, course);
            return courseRepository.save(course);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Course not found.");
        }
    }
}
