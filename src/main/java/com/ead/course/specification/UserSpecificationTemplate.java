package com.ead.course.specification;

import com.ead.course.entity.Course;
import com.ead.course.entity.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class UserSpecificationTemplate {

    @And({
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = LikeIgnoreCase.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "userType", spec = Equal.class),
    })
    public interface UserSpecification extends Specification<User> {}

    public static Specification<User> userByCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Course> course = query.from(Course.class);
            Expression<Collection<User>> courseUsers = course.get("users");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(root, courseUsers));
        };
    }

}
