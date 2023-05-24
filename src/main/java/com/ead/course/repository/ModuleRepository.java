package com.ead.course.repository;

import com.ead.course.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

    @Query("select module "
            + "from Module module "
            + "where module.course.courseId = :courseId")
    List<Module> findAllByCourseId(UUID courseId);

}
