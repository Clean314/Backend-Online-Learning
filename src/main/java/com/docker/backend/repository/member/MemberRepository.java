package com.docker.backend.repository.member;

import com.docker.backend.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByOrderByCreatedAtDesc();
    List<Member> findByNameContaining(String name);
}
