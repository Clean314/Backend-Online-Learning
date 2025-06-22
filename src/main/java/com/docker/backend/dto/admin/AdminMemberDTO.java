package com.docker.backend.dto.admin;

import com.docker.backend.domain.user.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminMemberDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String createdAt;
    private String updateAt;

    public AdminMemberDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = String.valueOf(member.getRole());
    }
}
