package com.docker.backend.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
    STUDENT("ROLE_STUDENT"),
    EDUCATOR("ROLE_EDUCATOR");

    private final String key;

    MemberRole(String key) {
        this.key = key;
    }
}