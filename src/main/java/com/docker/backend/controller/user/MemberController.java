package com.docker.backend.controller.user;

import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.dto.user.MemberDTO;
import com.docker.backend.service.member.MemberService;
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
    public ResponseEntity<MemberDTO> getProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(memberService.getProfile(userDetails.getMember()));
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }
}