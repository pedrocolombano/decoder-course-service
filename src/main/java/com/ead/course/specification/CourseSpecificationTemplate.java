package com.ead.course.specification;

import com.ead.course.entity.Course;
import com.ead.course.entity.CourseUser;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class CourseSpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class)
    })
    public interface CourseSpecification extends Specification<Course> {}

    public static Specification<Course> coursesByUser(final UUID userId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<Course, CourseUser> courseUsers = root.join("courseUsers");
            return criteriaBuilder.equal(courseUsers.get("userId"), userId);
        };
    }

}
