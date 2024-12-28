package com.yohagos.multiple_databases.keycloak.api;

import com.yohagos.multiple_databases.keycloak.services.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@Tag(name = "GroupApis")
public class GroupApi {

    private final GroupService groupService;


    @GetMapping
    public ResponseEntity<List<GroupRepresentation>> getGroups() {
        return ResponseEntity.ok(groupService.loadAvailableGroups());
    }

    @PutMapping("/assign/{userId}/{groupId}")
    public ResponseEntity<?> assignGroupById(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "groupId") String groupId
    ) {
        this.groupService.assignGroup(userId, groupId);
        return ResponseEntity.status(ACCEPTED).build();
    }

    @PutMapping("/leave/{userId}/{groupId}")
    public ResponseEntity<?> leaveGroupById(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "groupId") String groupId
    ) {
        this.groupService.leaveGroup(userId, groupId);
        return ResponseEntity.status(ACCEPTED).build();
    }
}
