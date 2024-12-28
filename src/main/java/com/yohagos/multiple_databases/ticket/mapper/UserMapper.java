package com.yohagos.multiple_databases.ticket.mapper;

import com.yohagos.multiple_databases.ticket.entities.UserEntity;
import com.yohagos.multiple_databases.ticket.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toUserResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .keycloakId(entity.getKeycloakId())
                .username(entity.getUsername())
                .skills(entity.getSkills())
                .build();
    }
}
