package com.docker.backend.model;

public enum MemberRole {
    STUDENT("ROLE_STUDENT"),
    EDUCATOR("ROLE_EDUCATOR");

    private final String key;

    MemberRole(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}