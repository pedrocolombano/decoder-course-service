package com.ead.course.specification;

import com.ead.course.entity.Course;
import com.ead.course.entity.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class CourseSpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class)
    })
    public interface CourseSpecification extends Specification<Course> {}

    public static Specification<Course> courseByUserId(final UUID userId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<User> user = query.from(User.class);
            Expression<Collection<Course>> userCourses = user.get("courses");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(user.get("userId"), userId),
                    criteriaBuilder.isMember(root, userCourses));
        };
    }

}
