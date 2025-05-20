package com.docker.backend.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    STUDENT("ROLE_STUDENT"),
    EDUCATOR("ROLE_EDUCATOR");

    private final String key;

    MemberRole(String key) {
        this.key = key;
    }
}