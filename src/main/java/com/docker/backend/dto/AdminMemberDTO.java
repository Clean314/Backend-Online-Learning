package com.docker.backend.dto;

import com.docker.backend.enums.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminMemberDTO {
    private Long id;
    private String name;
    private String email;
    private MemberRole role;
    private String createdAt;

}
