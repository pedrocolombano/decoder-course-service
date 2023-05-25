package com.ead.course.mapper;

import com.ead.course.dto.request.ModuleInsertDTO;
import com.ead.course.dto.response.ModuleDTO;
import com.ead.course.entity.Module;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModuleMapper {

    private final ModelMapper mapper;

    public Module fromModuleInsertDto(final ModuleInsertDTO moduleInsertDto) {
        return mapper.map(moduleInsertDto, Module.class);
    }

    public ModuleDTO fromEntity(final Module module) {
        return mapper.map(module, ModuleDTO.class);
    }

    public void map(final ModuleInsertDTO source, Module destination) {
        mapper.map(source, destination);
    }
}
