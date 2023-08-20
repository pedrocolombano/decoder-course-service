package com.ead.course.mapper;

import com.ead.course.dto.response.UserDTO;
import com.ead.course.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    public UserDTO from(final User user) {
        return mapper.map(user, UserDTO.class);
    }
}
