package com.docker.backend.controller;

import com.docker.backend.constant.ApplicationConstants;
import com.docker.backend.dto.LoginRequestDTO;
import com.docker.backend.dto.LoginResponseDTO;
import com.docker.backend.filter.JwtService;
import com.docker.backend.entity.Member;
import com.docker.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok()
                .header(ApplicationConstants.JWT_HEADER, token)
                .body(new LoginResponseDTO("Authenticated", token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Member member) {
        if (member.getRole() == null) {
            return ResponseEntity.badRequest().body("회원 역할(role)은 필수입니다.");
        }
        Member saved = memberService.register(member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered with ID: " + saved.getId());
    }
}
