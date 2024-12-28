package com.yohagos.multiple_databases.ticket.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userentity")
public class UserEntity extends BaseEntity {

    private String username;
    private String firstName;
    private String lastName;
    private String keycloakId;

    /*@ManyToMany(mappedBy = "assignedUsers", fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany(mappedBy = "assignedUsers", fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();*/

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Skills> skills = new HashSet<>();

    @Override
    public String toString() {
        return format("User=[id=%s, username=%s, keycloakId=%s]",
                getId(), getUsername(), getKeycloakId());
    }
}
