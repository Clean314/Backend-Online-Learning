package com.docker.backend.dto;

import com.docker.backend.enums.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String name;
    private String email;
    private String password;
    private MemberRole role;
    private String description;
}
