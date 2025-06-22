package com.docker.backend.service.member;

import com.docker.backend.dto.user.MemberDTO;
import com.docker.backend.dto.user.MemberUpdateDTO;
import com.docker.backend.domain.user.Member;
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

    public void register(Member member) {
        existsByEmail(member.getEmail());

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    public MemberDTO getProfile(Member member) {
        memberRepository.findById(member.getId());
        return new MemberDTO(
            member.getId(),  
            member.getName(), 
            member.getEmail(),
            member.getRole(),
            member.getDescription());
    }

    public void updateMember(Long memberId, MemberUpdateDTO dto) {
        Member member = getMemberOrThrow(memberId);
        
        member.setName(dto.getName());
        member.setDescription(dto.getDescription());
        memberRepository.save(member);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Member getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

}
