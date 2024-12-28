package com.yohagos.multiple_databases.ticket.services;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;

import java.util.List;

public interface PriorityLevelService {

    List<PriorityLevel> loadPriorityLevels();
}
