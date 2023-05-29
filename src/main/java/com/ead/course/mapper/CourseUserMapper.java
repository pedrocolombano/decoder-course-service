package com.ead.course.mapper;

import com.ead.course.dto.response.SubscribedUserDTO;
import com.ead.course.entity.CourseUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseUserMapper {

    private final ModelMapper mapper;

    public SubscribedUserDTO fromEntity(final CourseUser entity) {
        mapper.typeMap(CourseUser.class, SubscribedUserDTO.class)
                .addMapping(course -> course.getCourse().getCourseId(), SubscribedUserDTO::setCourseId);

        return mapper.map(entity, SubscribedUserDTO.class);
    }

}
