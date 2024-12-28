package com.yohagos.multiple_databases.keycloak.services;

import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface RoleService {

    void assignRole(String userId, String roleName);
    void deleteRole(String userId, String roleName);
    List<RoleRepresentation> loadAvailableRoles();

}
