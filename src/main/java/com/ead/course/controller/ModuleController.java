package com.ead.course.controller;

import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.dto.response.ModuleDTO;
import com.ead.course.entity.Module;
import com.ead.course.mapper.ModuleMapper;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
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
