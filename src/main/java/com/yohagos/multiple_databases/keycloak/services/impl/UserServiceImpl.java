package com.yohagos.multiple_databases.keycloak.services.impl;

import com.yohagos.multiple_databases.keycloak.model.CreateUserDto;
import com.yohagos.multiple_databases.keycloak.services.UserService;
import com.yohagos.multiple_databases.ticket.entities.UserEntity;
import com.yohagos.multiple_databases.ticket.repositories.UserRepository;
import com.yohagos.multiple_databases.ticket.services.UtilsService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.keycloak.representations.idm.CredentialRepresentation.PASSWORD;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.admin.clientId}")
    private String clientId;

    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final UtilsService utilsService;

    private UsersResource getUsersResources() {
        return keycloak.realm(realm).users();
    }

    @Override
    public void createUser(CreateUserDto addUser) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setLastName(addUser.lastName());
        user.setFirstName(addUser.firstName());
        user.setUsername(addUser.username());
        user.setEmail(addUser.username());
        user.setEmailVerified(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setValue(addUser.password());
        credentials.setType(PASSWORD);

        user.setCredentials(
                List.of(credentials)
        );

        UsersResource resources = getUsersResources();

        Response response = resources.create(user);

        if (!Objects.equals(201, response.getStatus()))
            throw new RuntimeException(format("Creating User %s caused an error", user.getUsername()));

        var createdUser = resources.searchByEmail(user.getUsername(), false);
        for (var usr: createdUser) {
            if (usr.getUsername().equals(user.getUsername())) {
                var localUser = UserEntity.builder()
                        .username(usr.getUsername())
                        .firstName(usr.getFirstName())
                        .lastName(usr.getLastName())
                        .keycloakId(usr.getId())
                        .createdBy(utilsService.getCurrentUsername())
                        .createdDate(LocalDateTime.now())
                        .build();
                userRepository.save(localUser);
            }
        }
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        return getUsersResources().list();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResources().delete(userId);

        var user = userRepository.findUserByKeycloakId(userId).orElseThrow();
        //user.ifPresent(userEntity -> userRepository.deleteById(userEntity.getId()));
        userRepository.deleteById(user.getId());
    }

    @Override
    public UserRepresentation getUserRepresentationByUserId(String userId) {
        return getUsersResources().get(userId).toRepresentation();
    }

    public UserResource getUserResourceByUserId(String userId) {
        return getUsersResources().get(userId);
    }

    @Override
    public List<RoleRepresentation> getUserRoles(String userId) {
        var currentUser = getUsersResources().get(userId);
        return currentUser.roles().realmLevel().listAll();
    }

    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return getUsersResources().get(userId).groups();
    }

    @Override
    public Map<String, Object> getUserDetails(String userId) {
        var user = getUserRepresentationByUserId(userId);
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("username", user.getUsername());
        userDetails.put("firstName", user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("roles", getAllRolesForUser(userId));

        Map<String, List<String>> aggregatedClientRoles = getClientRolesForUser(userId);
        mergeGroupClientRoles(userId, aggregatedClientRoles);
        userDetails.put("clientRoles", aggregatedClientRoles);
        userDetails.put("groups", getGroupsForUser(userId));

        return userDetails;
    }

    /*private List<String> getAllRealmRolesForUser(String userId) {
        Set<String> allRoles = new HashSet<>();

        allRoles.addAll(getUserResourceByUserId(userId)
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .toList());

        List<GroupRepresentation> allGroups = getGroupsForUser(userId);
        for (GroupRepresentation group : allGroups) {
            List<String> groupRoles = fetchGroupRoles(group);
            allRoles.addAll(groupRoles);
        }

        return allRoles.stream()
                .filter(str -> str.chars().allMatch(Character::isUpperCase))
                .toList();
    }*/

    private Set<String> getAllRolesForUser(String userId) {
        Set<String> allRoles = new HashSet<>(getEffectiveRolesForUser(userId));

        List<GroupRepresentation> userGroups = getUserResourceByUserId(userId).groups();
        for (GroupRepresentation group : userGroups) {
            allRoles.addAll(fetchGroupRoles(group));
        }

        return allRoles;
    }

    private List<String> fetchGroupRoles(GroupRepresentation group) {
        List<String> roles = new ArrayList<>(Optional.ofNullable(group.getRealmRoles()).orElse(List.of()));

        List<GroupRepresentation> subgroups = keycloak.realm(realm)
                .groups()
                .group(group.getId())
                .getSubGroups(0, Integer.MAX_VALUE, false);

        for (GroupRepresentation subgroup : subgroups) {
            roles.addAll(fetchGroupRoles(subgroup));
        }

        return roles;
    }

    private List<String> getEffectiveRolesForUser(String userId) {
        return getUserResourceByUserId(userId)
                .roles()
                .realmLevel()
                .listEffective()
                .stream()
                .map(RoleRepresentation::getName)
                .toList();
    }

    private void mergeGroupClientRoles(String userId, Map<String, List<String>> userClientRoles) {
        List<GroupRepresentation> groups = getGroupsForUser(userId);

        for (GroupRepresentation group : groups) {
            Map<String, List<String>> groupClientRoles = group.getClientRoles();
            if (groupClientRoles != null) {
                for (Map.Entry<String, List<String>> entry : groupClientRoles.entrySet()) {
                    String clientId = entry.getKey();
                    List<String> roles = entry.getValue();

                    if (!roles.isEmpty()) {
                        userClientRoles.merge(clientId, new ArrayList<>(roles), (existing, newRoles) -> {
                            existing.addAll(newRoles);
                            return existing.stream().distinct().toList();
                        });
                    }
                }
            }
        }
    }

    private Map<String, List<String>> getClientRolesForUser(String userId) {
        return keycloak.realm(realm).clients().findAll()
                .stream()
                .filter(client -> !getUserResourceByUserId(userId)
                        .roles()
                        .clientLevel(client.getId())
                        .listEffective()
                        .isEmpty()) // Nur Clients mit effektiven Rollen berücksichtigen
                .collect(Collectors.toMap(
                        ClientRepresentation::getId,
                        client -> getUserResourceByUserId(userId)
                                .roles()
                                .clientLevel(client.getId())
                                .listEffective()
                                .stream()
                                .map(RoleRepresentation::getName)
                                .toList()
                ));
    }

    private List<GroupRepresentation> getGroupsForUser(String userId) {
        List<GroupRepresentation> allGroups = new ArrayList<>();
        var groups = getUserResourceByUserId(userId).groups();

        for (var group : groups) {
            allGroups.add(group);
            fetchSubgroupsRecursively(group, allGroups);
        }

        return allGroups;
    }

    private void fetchSubgroupsRecursively(GroupRepresentation parentGroup, List<GroupRepresentation> allGroups) {
        List<GroupRepresentation> subgroups = keycloak.realm(realm)
                .groups()
                .group(parentGroup.getId())
                .getSubGroups(0, Integer.MAX_VALUE, false);

        for (var subgroup : subgroups) {
            allGroups.add(subgroup);
            fetchSubgroupsRecursively(subgroup, allGroups);
        }
    }
}
