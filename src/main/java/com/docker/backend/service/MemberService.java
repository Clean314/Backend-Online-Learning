package com.docker.backend.service;

import com.docker.backend.dto.MemberDTO;
import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    public MemberDTO getProfile(Member member) {
        memberRepository.findById(member.getId());
        return new MemberDTO(member.getId(), member.getEmail(), member.getName(), member.getRole(), member.getDescription());
    }

    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

}
