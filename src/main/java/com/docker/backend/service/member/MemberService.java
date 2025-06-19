package com.docker.backend.service.member;

import com.docker.backend.dto.user.MemberDTO;
import com.docker.backend.dto.user.MemberUpdateDTO;
import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional
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
        return new MemberDTO(member.getId(),  member.getName(), member.getEmail(), member.getRole(), member.getDescription());
    }

    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public void updateMember(Long memberId, MemberUpdateDTO dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        
        member.setName(dto.getName());
        member.setDescription(dto.getDescription());
        memberRepository.save(member);
    }

}
