package com.ead.course.feignclients;

import com.ead.commonlib.dto.response.PageResponseDTO;
import com.ead.course.dto.SubscribedUserDTO;
import com.ead.course.dto.response.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "EAD-API-GATEWAY", path = "/ead-user-auth/users")
public interface UserClient {

    @GetMapping
    PageResponseDTO<UserDTO> getAllUsersByCourse(@RequestParam UUID courseId, Pageable pageable);

    @GetMapping("/{userId}")
    UserDTO findById(@PathVariable UUID userId);

    @PostMapping("/{userId}/courses/subscribe")
    void subscribeUserIntoCourse(@PathVariable UUID userId, @RequestBody SubscribedUserDTO subscribedUserDTO);

    @DeleteMapping("/courses/{courseId}")
    void deleteUserCourseSubscriptions(@PathVariable UUID courseId);
}
