package com.docker.backend.service.admin;

import com.docker.backend.dto.admin.AdminCourseDTO;
import com.docker.backend.dto.admin.AdminCourseDetailDTO;
import com.docker.backend.dto.admin.AdminMemberDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Admin;
import com.docker.backend.entity.user.Member;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.MemberRole;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final EnrollmentRepository enrollmentRepository;
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


    // 사용자 수정 전에 보여주는 리스트
    public List<AdminMemberDTO> getMemeberList(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Member> all = memberRepository.findAllByOrderByCreatedAtDesc();
        return all.stream()
                .filter(m -> !(m instanceof Admin)) // 관리자 제외 일반 회원 필터
                .map(member -> {
                    AdminMemberDTO mem = new AdminMemberDTO(member);
                    LocalDateTime date = member.getUpdatedAt();
                    if (date == null) {
                        date = member.getCreatedAt();
                    }
                    mem.setUpdateAt(date.format(formatter));
                    return mem;
                })
                .toList();
    }
    // 사용자 정보수정(관리자)
    public void adminUpdateMember(Long memId, AdminMemberDTO member){

        Member mem = memberRepository.findById(memId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        mem.setName(member.getName());
        MemberRole role = MemberRole.valueOf(member.getRole().toUpperCase());
        mem.setRole(role);
        memberRepository.save(mem);
    }

    // 등록된 사용자 삭제
    public void adminDeleteMember(Long memId) {
        Member member = memberRepository.findById(memId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 존재하지 않습니다. ID: " + memId));
        if(member instanceof Student student){
            boolean isEnrolled = enrollmentRepository.existsByStudent(student);
            if(isEnrolled){
                throw new IllegalStateException("해당 학생은 강의신청이 되어있어 삭제를 할 수 없습니다.");
            }
        }

        memberRepository.delete(member);
    }

    // 등록된 강의 수정 전에 보여주는 리스트
    public List<AdminCourseDetailDTO> getCourseList(){
        List<Course> all = courseRepository.findAllByOrderByCreatedAtDesc();
        return all.stream()
                .map(AdminCourseDetailDTO::new)
                .toList();
    }
    // 등록된 강의 수정
    public AdminCourseDetailDTO adminUpdateCourse(Long courseId, AdminCourseDetailDTO course){
        Course co = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        if (course.getCourseName() != null) {
            co.setCourseName(course.getCourseName());
        }
        if (course.getCategory() != null) {
            co.setCategory(course.getCategory());
        }
        if (course.getDifficulty() != null) {
            co.setDifficulty(course.getDifficulty());
        }
        if (course.getPoint() != null) {
            co.setPoint(course.getPoint());
        }
        if (course.getMaxEnrollment() != null) {
            co.setMaxEnrollment(course.getMaxEnrollment());
        }
        Course save = courseRepository.save(co);
        return new AdminCourseDetailDTO(save);
    }

    // 등록된 강의 삭제
    public void adminDeleteCourse(Long courseId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강의가 존재하지 않습니다."));

        boolean isEnrolled = enrollmentRepository.existsByCourse(course);
        if(isEnrolled){
            throw new IllegalStateException("해당 강의는 이미 수강중인 학생이 있어 삭제할 수 없습니다.");
        }
        courseRepository.delete(course);
    }
    // 사용자 검색
    public List<AdminMemberDTO> searchMember(String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Member> all = memberRepository.findByNameContaining(name.trim().toUpperCase());
        if (all.isEmpty()) {
            throw new EntityNotFoundException("사용자가 없습니다.");
        }
        if(name.trim().isEmpty()){
            return new ArrayList<>();
        }
        return all.stream()
                .filter(m -> !(m instanceof Admin)) // 관리자 제외
                .map(member -> {
                    AdminMemberDTO dto = new AdminMemberDTO(member);
                    LocalDateTime date = member.getUpdatedAt();
                    if (date == null) {
                        date = member.getCreatedAt();
                    }
                    dto.setUpdateAt(date.format(formatter));
                    return dto;
                })
                .toList();
    }
    public List<AdminCourseDetailDTO> serchCourse(String courseName){
        List<Course> courses = courseRepository.findByCourseNameContaining(courseName.trim().toUpperCase());
        if(courses.isEmpty()){
            throw new EntityNotFoundException("강의가 없습니다");
        }
        if (courseName.trim().isEmpty()) {
            return new ArrayList<>(); // 또는 빈 리스트 반환
        }
        return courses.stream()
                .map(AdminCourseDetailDTO::new)
                .toList();
    }

}
