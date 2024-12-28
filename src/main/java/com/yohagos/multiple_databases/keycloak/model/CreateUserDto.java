package com.yohagos.multiple_databases.keycloak.model;

import static java.lang.String.format;

public record CreateUserDto(
        String username,
        String password,
        String firstName,
        String lastName
) {
    @Override
    public String toString() {
        return format("CreateUserDTO=[firstName=%s, lastName=%s, username=%s]",
                firstName(), lastName(), username());
    }
}
