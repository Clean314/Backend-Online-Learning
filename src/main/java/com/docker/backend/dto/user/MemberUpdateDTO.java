package com.docker.backend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateDTO {
    private String name;
    private String description;
} 