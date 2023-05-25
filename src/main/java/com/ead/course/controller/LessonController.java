package com.ead.course.controller;

import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.dto.response.LessonDTO;
import com.ead.course.entity.Lesson;
import com.ead.course.mapper.LessonMapper;
import com.ead.course.service.LessonService;
import com.ead.course.specification.LessonSpecificationTemplate;
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
@RequestMapping("/lessons")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping("/modules/{moduleId}")
    public ResponseEntity<Page<LessonDTO>> findAllByModule(@PathVariable UUID moduleId,
                                                           LessonSpecificationTemplate.LessonSpecification specification,
                                                           @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        final Specification<Lesson> lessonSpecification = LessonSpecificationTemplate.lessonByModuleId(moduleId)
                .and(specification);
        final Page<LessonDTO> lessons = lessonService.findAllByModuleId(lessonSpecification, pageable)
                .map(lessonMapper::fromEntity);
        return ResponseEntity.ok(lessons);
    }

    @GetMapping("/{lessonId}/modules/{moduleId}")
    public ResponseEntity<LessonDTO> findByIdAndModuleId(@PathVariable UUID lessonId,
                                                         @PathVariable UUID moduleId) {
        final Lesson lesson = lessonService.findByLessonIdAndModuleId(lessonId, moduleId);
        return ResponseEntity.ok(lessonMapper.fromEntity(lesson));
    }

    @PostMapping("/modules/{moduleId}")
    public ResponseEntity<LessonDTO> insert(@PathVariable UUID moduleId,
                                            @RequestBody LessonInsertDTO lessonInsertDTO) {
        final Lesson insertedLesson = lessonService.insert(moduleId, lessonInsertDTO);
        final LessonDTO response = lessonMapper.fromEntity(insertedLesson);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{lessonId}")
                .buildAndExpand(response.getLessonId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{lessonId}/modules/{moduleId}")
    public ResponseEntity<Void> deleteByIdAndModuleId(@PathVariable UUID lessonId,
                                                      @PathVariable UUID moduleId) {
        lessonService.deleteByIdAndModuleId(lessonId, moduleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{lessonId}/modules/{moduleId}")
    public ResponseEntity<LessonDTO> update(@PathVariable UUID lessonId,
                                            @PathVariable UUID moduleId,
                                            @RequestBody LessonInsertDTO lessonDto) {
        final Lesson updatedLesson = lessonService.update(lessonId, moduleId, lessonDto);
        return ResponseEntity.ok(lessonMapper.fromEntity(updatedLesson));
    }

}
