package com.docker.backend.controller.admin;

import com.docker.backend.dto.admin.AdminCourseDetailDTO;
import com.docker.backend.dto.admin.AdminMemberDTO;
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
        result.put("totalMember", adminService.getTOP10Members().size());
        result.put("totalCourses", adminService.getTOP10Course().size());

        return ResponseEntity.ok(result);
    }

    // 수정 전에 보여주는 리스트
    @GetMapping("/list/member-update")
    public ResponseEntity<List<AdminMemberDTO>> adminMemberUpdateList() {
        return ResponseEntity.ok(adminService.getMemeberList());
    }

    // 관리자가 사용자 이름, 역할? 수정
    @PatchMapping("/list/member-update/{id}")
    public ResponseEntity<String> adminUpdateMember(
            @PathVariable("id") Long memId,
            @RequestBody AdminMemberDTO dto) {
        try {
            adminService.adminUpdateMember(memId, dto);
            return ResponseEntity.ok("수정 완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

    // 사용자 삭제
    @DeleteMapping("/list/member-delete/{id}")
    public ResponseEntity<?> adminMemberDelete(@PathVariable("id") Long memId) {
        try{
            adminService.adminDeleteMember(memId);
            return ResponseEntity.ok("해당 사용자가 성공적으로 탈퇴처리가 되었습니다.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        } catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자의 상태를 확인 후 삭제해주세요.");
        }
    }

    // 업데이트 전에 보여주는 강의 리스트
    @GetMapping("/list/course-update")
    public ResponseEntity<List<AdminCourseDetailDTO>> getCourseList() {
        return ResponseEntity.ok(adminService.getCourseList());
    }

    // 등록된 강의 수정
    @PatchMapping("/list/course-update/{id}")
    public ResponseEntity<AdminCourseDetailDTO> adminUpdateCourse(@PathVariable("id") Long couId, @RequestBody AdminCourseDetailDTO course) {
        return ResponseEntity.ok(adminService.adminUpdateCourse(couId, course));
    }

    // 등록된 강의 삭제
    @DeleteMapping("/list/course-delete/{id}")
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
    @GetMapping("/list/find-member")
    public ResponseEntity<?> searchMember(@RequestParam String name) {
        try {
            List<AdminMemberDTO> serchMem = adminService.searchMember(name);
            return ResponseEntity.ok(serchMem);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 사용자가 없습니다.");
        }
    }
    // 강의 검색
    @GetMapping("/list/find-course")
    public ResponseEntity<?> searchCourse(@RequestParam String courseName) {
        try{
            List<AdminCourseDetailDTO> searchCourse = adminService.searchCourse(courseName);
            return ResponseEntity.ok(searchCourse);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 강의가 없습니다.");
        }

    }


}
