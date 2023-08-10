package com.ead.course.service.impl;

import com.ead.commonlib.exception.ResourceNotFoundException;
import com.ead.course.entity.Module;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class ModuleFetchServiceImpl implements ModuleFetchService {

    private final ModuleRepository moduleRepository;

    @Override
    @Transactional(readOnly = true)
    public Module findById(final UUID moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found."));
    }
}
