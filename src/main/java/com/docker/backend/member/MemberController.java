package com.docker.backend.member;

import com.docker.backend.model.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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