package com.yohagos.multiple_databases.ticket.services.impl;

import com.yohagos.multiple_databases.ticket.services.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilsServiceImpl implements UtilsService {

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
