package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.repositories.PriorityLevelRepository;
import com.yohagos.multiple_databases.ticket.services.PriorityLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityLevelServiceImpl implements PriorityLevelService {

    private final PriorityLevelRepository priorityLevelRepository;

    @Override
    public List<PriorityLevel> loadPriorityLevels() {
        return priorityLevelRepository.findAll();
    }
}
