package com.docker.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String email;
    private String name;
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}