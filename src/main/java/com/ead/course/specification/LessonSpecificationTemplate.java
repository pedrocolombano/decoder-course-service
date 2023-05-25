package com.ead.course.specification;

import com.ead.course.entity.Lesson;
import com.ead.course.entity.Module;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class LessonSpecificationTemplate {

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpecification extends Specification<Lesson> {}

    public static Specification<Lesson> lessonByModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(module.get("moduleId"), moduleId),
                    criteriaBuilder.isMember(root, moduleLessons)
            );
        };
    }

}
