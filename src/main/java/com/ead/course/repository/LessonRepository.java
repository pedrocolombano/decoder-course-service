package com.ead.course.repository;

import com.ead.course.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID>, JpaSpecificationExecutor<Lesson> {

    @Query("select lesson "
            + "from Lesson lesson "
            + "where lesson.module.moduleId = :moduleId")
    List<Lesson> findAllByModule(UUID moduleId);

    Optional<Lesson> findByLessonIdAndModuleModuleId(UUID lessonId, UUID moduleId);

}
