package com.ead.course.controller;

import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.dto.response.ModuleDTO;
import com.ead.course.entity.Module;
import com.ead.course.mapper.ModuleMapper;
import com.ead.course.service.ModuleService;
import com.ead.course.specification.ModuleSpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/modules")
@AllArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleMapper moduleMapper;

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Page<ModuleDTO>> findAllByCourse(@PathVariable UUID courseId,
                                                           ModuleSpecificationTemplate.ModuleSpecification specification,
                                                           @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        final Specification<Module> moduleSpecification = ModuleSpecificationTemplate.moduleByCourseId(courseId)
                .and(specification);
        final Page<ModuleDTO> modules = moduleService.findAllByCourseId(moduleSpecification, pageable)
                .map(moduleMapper::fromEntity);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("/{moduleId}/courses/{courseId}")
    public ResponseEntity<ModuleDTO> findById(@PathVariable UUID moduleId,
                                              @PathVariable UUID courseId) {
        final Module module = moduleService.findByModuleIdAndCourseId(moduleId, courseId);
        return ResponseEntity.ok(moduleMapper.fromEntity(module));
    }

    @PostMapping("/courses/{courseId}")
    public ResponseEntity<ModuleDTO> insert(@PathVariable UUID courseId,
                                            @RequestBody ModuleInsertDTO moduleInsertDTO) {
        final Module insertedModule = moduleService.insert(courseId, moduleInsertDTO);
        final ModuleDTO response = moduleMapper.fromEntity(insertedModule);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{moduleId}")
                .buildAndExpand(response.getModuleId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID moduleId) {
        moduleService.deleteById(moduleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<Void> deleteAllByCourseId(@PathVariable UUID courseId) {
        moduleService.deleteAllByCourseId(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{moduleId}/courses/{courseId}")
    public ResponseEntity<ModuleDTO> update(@PathVariable UUID moduleId,
                                            @PathVariable UUID courseId,
                                            @RequestBody ModuleInsertDTO moduleInsertDTO) {
        final Module updatedModule = moduleService.update(moduleId, courseId, moduleInsertDTO);
        return ResponseEntity.ok(moduleMapper.fromEntity(updatedModule));
    }

}
