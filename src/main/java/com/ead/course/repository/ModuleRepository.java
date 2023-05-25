package com.ead.course.repository;

import com.ead.course.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID>, JpaSpecificationExecutor<Module> {

    @Query("select module "
            + "from Module module "
            + "where module.course.courseId = :courseId")
    List<Module> findAllByCourseId(UUID courseId);

    @Query("select module "
    + "from Module module "
    + "where module.moduleId = :moduleId "
    + "and module.course.courseId = :courseId")
    Optional<Module> findByModuleIdAndCourseId(UUID moduleId, UUID courseId);
}
