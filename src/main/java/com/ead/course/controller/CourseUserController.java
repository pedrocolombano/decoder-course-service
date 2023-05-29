package com.ead.course.controller;

import com.ead.course.dto.response.UserDTO;
import com.ead.course.service.CourseUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Log4j2
public class CourseUserController {

    private final CourseUserService courseUserService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> findAllUsersByCourse(@PathVariable UUID courseId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(courseUserService.getAllUsersByCourse(courseId, pageable));
    }

}
