package com.ead.course.repository;

import com.ead.course.entity.Course;
import com.ead.course.entity.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, UUID> {

    boolean existsByCourseAndUserId(Course course, UUID userId);

}
