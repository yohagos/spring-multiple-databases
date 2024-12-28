package com.yohagos.multiple_databases.keycloak.services;

import com.yohagos.multiple_databases.keycloak.model.CreateUserDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Map;

public interface UserService {

    void createUser(CreateUserDto addUser);

    void deleteUserById(String userId);

    UserRepresentation getUserRepresentationByUserId(String userId);

    Map<String, Object> getUserDetails(String userId);

    List<UserRepresentation> getAllUsers();

    List<RoleRepresentation> getUserRoles(String userId);

    List<GroupRepresentation> getUserGroups(String userId);

    UserResource getUserResourceByUserId(String userId);
}
