package com.ead.course.proxy;

import com.ead.course.dto.SubscribedUserDTO;
import com.ead.course.dto.response.PageResponseDTO;
import com.ead.course.dto.response.UserDTO;
import com.ead.course.exception.ProxyException;
import com.ead.course.exception.ResourceNotFoundException;
import com.ead.course.exception.InvalidSubscriptionException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class UserProxy {

    private final RestTemplate restTemplate;
    private final Environment environment;

    public Page<UserDTO> getAllUsersByCourse(final UUID courseId, final Pageable pageable) {
        final String url = buildUserUrl(courseId, pageable).replace(": ", ",");
        log.info("Request URL: {}", url);
        try {
            ParameterizedTypeReference<PageResponseDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<PageResponseDTO<UserDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error during request into: {}", url);
            throw new ProxyException("Could not fetch results from user service.");
        }
    }

    private String getUserServiceHost() {
        return environment.getProperty("ead.user.service.host");
    }

    private String buildUserUrl(final UUID userId, final Pageable pageable) {
        return getUserServiceHost()
                + "/users?courseId="
                + userId
                + "&page="
                + pageable.getPageNumber()
                + "&size="
                + pageable.getPageSize();
    }

    public UserDTO findById(final UUID userId) {
        final String url = getUserServiceHost() + "/users/" + userId;
        try {
            log.info("Fetching user by id {}.", userId);
            return restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class).getBody();
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getRawStatusCode()) {
                log.error("User {} not found in auth user service.", userId);
                throw new ResourceNotFoundException("User not found.");
            }
            log.error("Error during request into: {}", url);
            throw new ProxyException("Could not fetch user by id from user service.");
        }
    }

    public void subscribeUserIntoCourse(final UUID userId, final UUID courseId) {
        final String url = getUserServiceHost() + "/users/" + userId + "/courses/subscribe";
        final SubscribedUserDTO subscribedUserRequest = new SubscribedUserDTO(courseId, userId);
        try {
            log.info("Subscribing user {} on auth user service.", userId);
            restTemplate.postForObject(url, subscribedUserRequest, Void.class);
        } catch (HttpClientErrorException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getRawStatusCode()) {
                log.error("User {} not found in auth user service.", userId);
                throw new ResourceNotFoundException("User not found.");
            }
            else if (HttpStatus.BAD_REQUEST.value() == e.getRawStatusCode()) {
                log.error("User {} is already subscribed into course in auth user service.", userId);
                throw new InvalidSubscriptionException("User is already subscribed into course.");
            }
            log.error("Error during request into: {}", url);
            throw new ProxyException("Could not fetch user by id from user service.");
        }
    }

}
