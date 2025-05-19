package com.docker.backend.controller.user;

import com.docker.backend.entity.user.Member;
import com.docker.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<Member> getProfile(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberService.findByEmail(email);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }
}