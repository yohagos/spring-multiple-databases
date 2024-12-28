package com.yohagos.multiple_databases.ticket.apis;

import com.yohagos.multiple_databases.ticket.entities.PriorityLevel;
import com.yohagos.multiple_databases.ticket.services.PriorityLevelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/priority-level")
@RequiredArgsConstructor
@Tag(name = "PriorityLevelApis")
public class PriorityLevelApi {

    private final PriorityLevelService priorityLevelService;

    @GetMapping
    public ResponseEntity<List<PriorityLevel>> getAvailablePriorityLevels() {
        return ResponseEntity.ok(priorityLevelService.loadPriorityLevels());
    }
}
