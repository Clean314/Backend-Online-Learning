package com.docker.backend.config;

import com.docker.backend.model.MemberRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final Member member;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
    }

    public String getName() {
        return member.getName();
    }

    public Long getId() {
        return member.getId();
    }

    public MemberRole getRole() {
        return member.getRole();
    }
}