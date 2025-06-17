package com.docker.backend.controller.user;

import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.dto.user.MemberDTO;
import com.docker.backend.dto.user.MemberUpdateDTO;
import com.docker.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 API 컨트롤러
 * - 사용자 프로필 조회
 * - 사용자 정보 수정 (이름, 소개)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 프로필 조회 API
     * - 현재 로그인한 사용자의 프로필 정보를 반환
     * - CustomUserDetails를 통해 인증된 사용자 정보를 가져옴
     */
    @GetMapping("/profile")
    public ResponseEntity<MemberDTO> getProfile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(memberService.getProfile(userDetails.getMember()));
    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }

    /**
     * 사용자 정보 수정 API
     * - 사용자의 이름과 소개 정보만 수정 가능
     * - STUDENT, EDUCATOR 권한 모두 접근 가능
     * - CustomUserDetails를 통해 인증된 사용자 정보를 가져옴
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateDTO dto,
            Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        memberService.updateMember(memberId, dto);
        return ResponseEntity.ok().build();
    }
}