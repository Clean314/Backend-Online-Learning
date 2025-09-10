package com.docker.backend.controller.user;

import com.docker.backend.constant.ApplicationConstants;
import com.docker.backend.dto.user.LoginRequestDTO;
import com.docker.backend.dto.user.LoginResponseDTO;
import com.docker.backend.dto.user.RegisterRequestDTO;
import com.docker.backend.domain.user.Educator;
import com.docker.backend.domain.user.Student;
import com.docker.backend.domain.enums.MemberRole;
import com.docker.backend.filter.JwtService;
import com.docker.backend.domain.user.Member;
import com.docker.backend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Login/Register API", description = "회원 가입 및 로그인")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MemberService memberService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "유저이름과 비밀번호로 로그인을 합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = @Content(schema = @Schema(implementation = LoginRequestDTO.class))
    )
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok()
                .header(ApplicationConstants.JWT_HEADER, "Bearer " + token)
                .body(new LoginResponseDTO("Authenticated"));
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "유저이름, 비밀번호, 이메일, 역할로 회원가입을 합니다.")
    @ApiResponse(
            responseCode = "201",
            description = "회원가입 성공",
            content = @Content(schema = @Schema(implementation = RegisterRequestDTO.class))
    )
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
