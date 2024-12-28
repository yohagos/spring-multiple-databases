package com.yohagos.multiple_databases.keycloak.services.impl;

import com.yohagos.multiple_databases.keycloak.services.RoleService;
import com.yohagos.multiple_databases.keycloak.services.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final Keycloak keycloak;
    private final UserService userService;
    @Value("${app.keycloak.realm}")
    private String realm;

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }

    @Override
    public List<RoleRepresentation> loadAvailableRoles() {
        return getRolesResource().list(false);
    }

    @Override
    public void assignRole(String userId, String roleName) {
        UserResource user = userService.getUserResourceByUserId(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(representation));
    }

    @Override
    public void deleteRole(String userId, String roleName) {
        UserResource userResource = userService.getUserResourceByUserId(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        userResource.roles().realmLevel().remove(singletonList(representation));
    }
}
