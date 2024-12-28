package com.yohagos.multiple_databases.keycloak.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
}
