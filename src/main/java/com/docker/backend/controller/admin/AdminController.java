package com.docker.backend.controller.admin;

import com.docker.backend.dto.admin.AdminCourseDetailDTO;
import com.docker.backend.dto.admin.AdminMemberDTO;
import com.docker.backend.entity.user.Member;
import com.docker.backend.service.admin.AdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // 관리자 대시보드 최근가입, 최근등록 강의 10개, 총 인원, 총 강의개수
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> adminDashBoard() {
        Map<String, Object> result = new HashMap<>();
        result.put("members", adminService.getTOP10Members());
        result.put("courses", adminService.getTOP10Course());
        result.put("totalMember", adminService.getTOP10Members().stream().count());
        result.put("totalCourses", adminService.getTOP10Course().stream().count());

        return ResponseEntity.ok(result);
    }

    // 수정 전에 보여주는 리스트
    @GetMapping("/list/memberUpdate")
    public ResponseEntity<List<AdminMemberDTO>> adminMemberUpdateList() {
        return ResponseEntity.ok(adminService.getMemeberList());
    }

    // 관리자가 사용자 이름, 역할? 수정
    @PatchMapping("/list/memberUpdate/{id}")
    public ResponseEntity<Void> adminUpdateMember(@PathVariable("id") Long memId, @RequestBody Member member) {
        adminService.adminUpdateMember(memId, member);
        return ResponseEntity.ok().build();
    }

    // 사용자 삭제
    @DeleteMapping("/list/memberDelete/{id}")
    public ResponseEntity<?> adminMemberDelete(@PathVariable("id") Long memId) {
        try{
            adminService.adminDeleteMember(memId);
            return ResponseEntity.ok("해당 학생이 성공적으로 탈퇴처리가 되었습니다.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자의 상태를 확인 후 삭제해주세요.");
        }
    }

    // 업데이트 전에 보여주는 강의 리스트
    @GetMapping("/list/courseUpdate")
    public ResponseEntity<List<AdminCourseDetailDTO>> adminCourseUpdateList() {
        return ResponseEntity.ok(adminService.getCourseList());
    }

    // 등록된 강의 수정
    @PatchMapping("/list/courseUpdate/{id}")
    public ResponseEntity<Void> adminUpdateCourse(@PathVariable("id") Long couId, @RequestBody AdminCourseDetailDTO course) {
        adminService.adminUpdateCourse(couId, course);
        return ResponseEntity.ok().build();
    }

    // 등록된 강의 삭제
    @DeleteMapping("/list/courseDelete/{id}")
    public ResponseEntity<?> adminDeleteCourse(@PathVariable("id") Long courseId) {
        try {
            adminService.adminDeleteCourse(courseId);
            return ResponseEntity.ok("강의가 성공적으로 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 강의를 찾을 수 없습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 강의의 상태를 확인 후 삭제해주세요.");
        }
    }

    // 사용자 검색
    @GetMapping("/list/findMember")
    public ResponseEntity<?> adminFindMember(@RequestParam String name) {
        try {
            List<AdminMemberDTO> serchMem = adminService.searchMember(name);
            return ResponseEntity.ok(serchMem);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 사용자가 없습니다.");
        }
    }
    // 강의 검색
    @GetMapping("/list/findCourse")
    public ResponseEntity<?> adminFindCourse(@RequestParam String courseName) {
        try{
            List<AdminCourseDetailDTO> serchCourse = adminService.serchCourse(courseName);
            return ResponseEntity.ok(serchCourse);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 강의가 없습니다.");
        }

    }


}
