package com.docker.backend.controller;

import com.docker.backend.entity.Member;
import com.docker.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {
    
    // 앞에 auth 있는 이유는 auth가 토큰 보호 필요없는 링크라서

    private final MemberRepository memberRepository;

    @GetMapping("/auth/all")
    public List<Member> test(){
        return memberRepository.findAll();
    }

    @GetMapping("/auth/find/{email}")
    public Optional<Member> test1(@PathVariable String email){
        return memberRepository.findByEmail(email);
    }
}
