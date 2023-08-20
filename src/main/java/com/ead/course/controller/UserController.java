package com.ead.course.controller;

import com.ead.course.dto.response.UserDTO;
import com.ead.course.mapper.UserMapper;
import com.ead.course.service.UserService;
import com.ead.course.specification.UserSpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Page<UserDTO>> findAllUsersByCourse(@PathVariable UUID courseId,
                                                              UserSpecificationTemplate.UserSpecification specification,
                                                              @PageableDefault(size = 20) Pageable pageable) {
        final Page<UserDTO> users = userService.findAllUsersByCourseId(courseId, specification, pageable)
                .map(userMapper::from);
        return ResponseEntity.ok(users);
    }


}
