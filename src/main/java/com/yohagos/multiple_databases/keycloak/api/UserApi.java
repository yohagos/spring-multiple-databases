package com.yohagos.multiple_databases.keycloak.api;

import com.yohagos.multiple_databases.keycloak.model.CreateUserDto;
import com.yohagos.multiple_databases.keycloak.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "UserApi")
public class UserApi {

    private final UserService userService;

    /*
        Get Endpoints
    */

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(
            @PathVariable(name = "userId") String userId
    ) {
        return ResponseEntity.ok(userService.getUserDetails(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserRepresentation>> getAvailableUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/roles/{userId}")
    public ResponseEntity<List<RoleRepresentation>> getRolesByUserId(
            @PathVariable(name = "userId") String userId
    ) {
        return ResponseEntity.ok(userService.getUserRoles(userId));
    }

    @GetMapping("/groups/{userId}")
    public ResponseEntity<List<GroupRepresentation>> getGroupsByUserId(
            @PathVariable(name = "userId") String userId
    ) {
        return ResponseEntity.ok(userService.getUserGroups(userId));
    }

    /*
        Post Endpoints
    */

    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserDto user
    ) {
        userService.createUser(user);
        return ResponseEntity.status(CREATED).build();
    }

    /*
        Put Endpoints
    */



    /*
        Delete Endpoints
    */

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeUserById(
            @PathVariable(name = "userId") String userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(ACCEPTED).build();
    }

}
