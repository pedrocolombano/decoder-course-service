package com.ead.course.specification;

import com.ead.course.entity.Course;
import com.ead.course.entity.Module;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class ModuleSpecificationTemplate {

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpecification extends Specification<Module> {}

    public static Specification<Module> moduleByCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(root, courseModules)
            );
        };
    }

}
