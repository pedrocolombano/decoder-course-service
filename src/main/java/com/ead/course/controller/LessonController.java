package com.ead.course.controller;

import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.dto.response.LessonDTO;
import com.ead.course.entity.Lesson;
import com.ead.course.mapper.LessonMapper;
import com.ead.course.service.LessonService;
import lombok.AllArgsConstructor;
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

}
