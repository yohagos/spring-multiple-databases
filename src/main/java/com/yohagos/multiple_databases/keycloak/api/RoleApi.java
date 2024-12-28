package com.yohagos.multiple_databases.keycloak.api;

import com.yohagos.multiple_databases.keycloak.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "RoleApi")
public class RoleApi {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleRepresentation>> getRoles() {
        return ResponseEntity.ok(roleService.loadAvailableRoles());
    }

    @PutMapping("/assign/{userId}/{roleId}")
    public ResponseEntity<?> assignUserRole(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "roleId") String roleId
    ) {
        roleService.assignRole(userId, roleId);
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/remove/{userId}/{roleId}")
    public ResponseEntity<?> deleteUserRole(
            @PathVariable(name = "userId") String userId,
            @PathVariable(name = "roleId") String roleId
    ) {
        roleService.deleteRole(userId, roleId);
        return ResponseEntity.status(CREATED).build();
    }
}
