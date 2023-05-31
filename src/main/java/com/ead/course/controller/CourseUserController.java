package com.ead.course.controller;

import com.ead.course.dto.SubscribedUserDTO;
import com.ead.course.dto.request.CourseSubscriptionDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.CourseUser;
import com.ead.course.mapper.CourseUserMapper;
import com.ead.course.service.CourseUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/courses/{courseId}/users")
@Log4j2
public class CourseUserController {

    private final CourseUserService courseUserService;
    private final CourseUserMapper courseUserMapper;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllUsersByCourse(@PathVariable UUID courseId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(courseUserService.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<SubscribedUserDTO> subscribeUserIntoCourse(@PathVariable UUID courseId, @RequestBody CourseSubscriptionDTO subscriptionDTO) {
        final CourseUser createdSubscription = courseUserService.subscribeUserIntoCourse(courseId, subscriptionDTO);
        final SubscribedUserDTO response = courseUserMapper.fromEntity(createdSubscription);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
