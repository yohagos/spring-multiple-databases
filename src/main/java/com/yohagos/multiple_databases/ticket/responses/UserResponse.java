package com.yohagos.multiple_databases.ticket.responses;

import com.yohagos.multiple_databases.ticket.entities.Skills;
import lombok.*;

import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String keycloakId;
    private Set<Skills> skills;

    @Override
    public String toString() {
        return format("UserResponse=[id=%s, username=%s, firstName=%s, lastName=%s, keycloakId=%s, skills=%s]",
                getId(), getUsername(), getFirstName(), getLastName(), getKeycloakId(), getSkills());
    }
}
