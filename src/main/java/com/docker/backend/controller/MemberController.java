package com.docker.backend.controller;

<<<<<<< Updated upstream:src/main/java/com/docker/backend/member/MemberController.java
import com.docker.backend.model.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import com.docker.backend.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
>>>>>>> Stashed changes:src/main/java/com/docker/backend/controller/MemberController.java

@RestController
@RequestMapping
public class MemberController {

    @PostMapping("/register")
    public void memberRegister(Member member) {

    }

    @PostMapping("/login")
    public void memberLogin(Member member) {
    }

    @GetMapping("/logout")
    public void memberLogout() {
    }
}