package com.yohagos.multiple_databases.keycloak.services.impl;

import com.yohagos.multiple_databases.keycloak.services.GroupService;
import com.yohagos.multiple_databases.keycloak.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    @Value("${app.keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final UserService userService;

    private GroupsResource getGroups() {
        return keycloak.realm(realm).groups();
    }

    @Override
    public void assignGroup(String userId, String groupId) {
        var userResource = userService.getUserResourceByUserId(userId);
        userResource.joinGroup(groupId);
    }

    @Override
    public void leaveGroup(String userId, String groupId) {
        var userResource = userService.getUserResourceByUserId(userId);
        userResource.leaveGroup(groupId);

    }

    @Override
    public List<GroupRepresentation> loadAvailableGroups() {
        List<GroupRepresentation> allGroups = new ArrayList<>();
        var parentGroups = getGroups().groups();

        for (var group : parentGroups) {
            allGroups.add(group);
            fetchChildGroups(group, allGroups);
        }
        return allGroups;
    }

    private void fetchChildGroups(
            GroupRepresentation parentGroup,
            List<GroupRepresentation> allGroups
    ) {
        var childGroups = getGroups().group(parentGroup.getId())
                .getSubGroups(0, Integer.MAX_VALUE, false);

        for(var childGroup : childGroups) {
            allGroups.add(childGroup);
            fetchChildGroups(childGroup, allGroups);
        }

    }
}
