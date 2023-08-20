package com.ead.course.controller;

import com.ead.course.dto.request.CourseInsertDTO;
import com.ead.course.dto.response.CourseDTO;
import com.ead.course.entity.Course;
import com.ead.course.mapper.CourseMapper;
import com.ead.course.service.CourseService;
import com.ead.course.specification.CourseSpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@Log4j2
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> findAll(CourseSpecificationTemplate.CourseSpecification specification,
                                                   @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<CourseDTO> courses = courseService.findAll(specification, pageable)
                .map(courseMapper::fromEntity);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> findById(@PathVariable UUID courseId) {
        final Course course = courseService.findById(courseId);
        return ResponseEntity.ok(courseMapper.fromEntity(course));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> insert(@RequestBody @Valid CourseInsertDTO courseInsertDTO) {
        final Course createdCourse = courseService.insert(courseInsertDTO);
        final CourseDTO response = courseMapper.fromEntity(createdCourse);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{courseId}")
                .buildAndExpand(response.getCourseId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID courseId) {
        courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> update(@PathVariable UUID courseId,
                                            @RequestBody @Valid CourseInsertDTO courseInsertDTO) {
        final Course updatedCourse = courseService.update(courseId, courseInsertDTO);
        return ResponseEntity.ok(courseMapper.fromEntity(updatedCourse));
    }

}
