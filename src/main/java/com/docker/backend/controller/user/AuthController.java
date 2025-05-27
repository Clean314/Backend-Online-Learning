package com.docker.backend.controller.user;

import com.docker.backend.constant.ApplicationConstants;
import com.docker.backend.dto.member.LoginRequestDTO;
import com.docker.backend.dto.member.LoginResponseDTO;
import com.docker.backend.dto.member.RegisterRequestDTO;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.MemberRole;
import com.docker.backend.filter.JwtService;
import com.docker.backend.entity.user.Member;
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
                .header(ApplicationConstants.JWT_HEADER, "Bearer " + token)
                .body(new LoginResponseDTO("Authenticated", token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO req) {
        if (req.getRole() == null) {
            return ResponseEntity.badRequest().body("회원 역할(role)은 필수입니다.");
        }

        if (memberService.existsByEmail(req.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 이메일입니다.");
        }

        Member member = switch (MemberRole.valueOf(req.getRole().name().toUpperCase())) {
            case MemberRole.STUDENT -> new Student();
            case MemberRole.EDUCATOR -> new Educator();
            default -> throw new IllegalArgumentException("유효하지 않은 역할입니다.");
        };

        member.setEmail(req.getEmail());
        member.setPassword(req.getPassword());
        member.setName(req.getName());
        member.setRole(MemberRole.valueOf(req.getRole().name().toUpperCase()));
        memberService.register(member);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
    }

}
