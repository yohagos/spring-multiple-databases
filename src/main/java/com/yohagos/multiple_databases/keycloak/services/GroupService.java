package com.yohagos.multiple_databases.keycloak.services;

import org.keycloak.representations.idm.GroupRepresentation;

import java.util.List;

public interface GroupService {

    void assignGroup(String userId, String groupId);
    void leaveGroup(String userId, String groupId);

    List<GroupRepresentation> loadAvailableGroups();

}
