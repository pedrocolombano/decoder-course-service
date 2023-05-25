package com.ead.course.mapper;

import com.ead.course.dto.request.LessonInsertDTO;
import com.ead.course.dto.response.LessonDTO;
import com.ead.course.entity.Lesson;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LessonMapper {

    private final ModelMapper mapper;

    public Lesson fromLessonInsertDto(final LessonInsertDTO lessonInsertDTO) {
        return mapper.map(lessonInsertDTO, Lesson.class);
    }

    public LessonDTO fromEntity(final Lesson entity) {
        return mapper.map(entity, LessonDTO.class);
    }

}
