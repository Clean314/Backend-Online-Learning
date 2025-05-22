package com.docker.backend.config;

import com.docker.backend.entity.user.Educator;
import com.docker.backend.entity.user.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    public Educator getEducator(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return (Educator) userDetails.getMember();
    }

    public Student getStudent(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return (Student) userDetails.getMember();
    }
}

