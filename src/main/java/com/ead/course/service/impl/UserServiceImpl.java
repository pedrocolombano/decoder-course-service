package com.ead.course.service.impl;

import com.ead.course.entity.User;
import com.ead.course.repository.UserRepository;
import com.ead.course.service.UserService;
import com.ead.course.specification.UserSpecificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllUsersByCourseId(final UUID courseId,
                                             final UserSpecificationTemplate.UserSpecification specification,
                                             final Pageable pageable) {
        Specification<User> userByCourseSpecification = UserSpecificationTemplate.userByCourseId(courseId).and(specification);
        return userRepository.findAll(userByCourseSpecification, pageable);
    }

    @Override
    @Transactional
    public void insertUser(final User user) {
        userRepository.save(user);
    }
}
