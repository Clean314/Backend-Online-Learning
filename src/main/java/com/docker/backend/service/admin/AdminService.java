package com.docker.backend.service.admin;

import com.docker.backend.dto.admin.AdminCourseDTO;
import com.docker.backend.dto.admin.AdminCourseDetailDTO;
import com.docker.backend.dto.admin.AdminMemberDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Admin;
import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.admin.AdminMemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMemberRepository adminRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    // 관리자가 아닌 member전체 가입일 순으로 나옴 10개
    public List<AdminMemberDTO> getTOP10Members() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Member> all = memberRepository.findAllByOrderByCreatedAtDesc();

        return all.stream()
                .filter(m -> !(m instanceof Admin))
                .limit(10)
                .map(member -> {
                    AdminMemberDTO dto = new AdminMemberDTO(member);
                    dto.setCreatedAt(member.getCreatedAt().format(formatter));
                    return dto;
                })
                .toList();
    }

    // 관리자 대시보드 TOP10 강의
    public List<AdminCourseDTO> getTOP10Course(){
        List<Course> all = courseRepository.findAllByOrderByCreatedAtDesc();

        return all.stream()
                .limit(10)
                .map(AdminCourseDTO::new)
                .toList();
    }


    // 사용자 정보수정(관리자)
    public void adminUpdateMember(Long memId, Member member){

        Member mem = memberRepository.findById(memId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        mem.setName(member.getName());
        mem.setRole(member.getRole());
        memberRepository.save(mem);
    }
    // 사용자 수정 전에 보여주는 리스트
    public List<AdminMemberDTO> getMemeberList(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Member> all = memberRepository.findAllByOrderByCreatedAtDesc();
        return all.stream()
                .filter(m -> !(m instanceof Admin)) // 관리자 제외 일반 회원 필터
                .map(member -> {
                    AdminMemberDTO mem = new AdminMemberDTO(member);
                    String date =
                            member.getUpdatedAt() != null
                                    ? member.getUpdatedAt().format(formatter)
                                    : member.getCreatedAt().format(formatter);
                    mem.setUpdateAt(date);
                    return mem;
                })
                .toList();
    }

    // 등록된 강의 수전 전에 보여주는 리스트
    public List<AdminCourseDetailDTO> getCourseList(){
        List<Course> all = courseRepository.findAllByOrderByCreatedAtDesc();
        return all.stream()
                .map(AdminCourseDetailDTO::new)
                .toList();
    }

    public void adminUpdateCourse(Long couId, Course course){
        Course co = courseRepository.findById(couId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        co.setCategory(course.getCategory());
        co.setDifficulty(course.getDifficulty());
        co.setPoint(course.getPoint());
        co.
    }

}
