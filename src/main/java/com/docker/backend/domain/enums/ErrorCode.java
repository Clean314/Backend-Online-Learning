package com.docker.backend.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND_MEMBER("존재하지 않는 유저입니다."),
    SAME_EMAIL("이미 사용중인 이메일 입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
