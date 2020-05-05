package com.authenticator.account;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN ("Admin"),
    OPERATOR ("Operator"),
    CHIEF ("Chief"),
    USER ("Regular user");

    private final String roleName;

    Role(String role) {
        this.roleName = role;
    }
}
