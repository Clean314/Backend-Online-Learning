package com.docker.backend.service.admin;

import com.docker.backend.dto.AdminMemberDTO;
import com.docker.backend.entity.user.Admin;
import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.admin.AdminMemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMemberRepository adminRepository;

    // 관리자가 아닌 member전체 가입일 순으로 나옴 10개
    public List<AdminMemberDTO> getAllMembers(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return adminRepository.orderByTop10Member().stream()
                .map(member -> new AdminMemberDTO(
                        member.getId(),
                        member.getName(),
                        member.getEmail(),
                        member.getRole(),
                        member.getCreatedAt().format(formatter)  // LocalDateTime → String 변환
                ))
                .toList();
    }




}
