package com.docker.backend.dto;

import com.docker.backend.model.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MemberDTO {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role", nullable = false)
    private MemberRole role;

    private String email;
    private String name;
    private String password;
}