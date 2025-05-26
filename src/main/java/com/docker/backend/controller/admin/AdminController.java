package com.docker.backend.controller.admin;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.admin.AdminCourseDetailDTO;
import com.docker.backend.dto.admin.AdminMemberDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Member;
import com.docker.backend.service.admin.AdminService;
import com.docker.backend.service.course.CourseService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<AdminMemberDTO>> adminMemberUpdateList(){
        return ResponseEntity.ok(adminService.getMemeberList());
    }

    // 관리자가 사용자 이름, 역할? 수정
    @PatchMapping("/list/memberUpdate/{id}")
    public ResponseEntity<Void> adminUpdateMember(@PathVariable("id") Long memId, @RequestBody Member member){
        adminService.adminUpdateMember(memId, member);
        return ResponseEntity.ok().build();
    }
    // 사용자 삭제
    @DeleteMapping("/list/memberDelete/{id}")
    public ResponseEntity<Void> adminMemberDelete(@PathVariable("id") Long memId) {
        adminService.adminDeleteMember(memId);
        return ResponseEntity.noContent().build();
    }

    // 업데이트 전에 보여주는 강의 리스트
    @GetMapping("/list/courseUpdate")
    public ResponseEntity<List<AdminCourseDetailDTO>> adminCourseUpdateList(){
        return ResponseEntity.ok(adminService.getCourseList());
    }
    // 등록된 강의 수정
    @PatchMapping("/list/courseUpdate/{id}")
    public ResponseEntity<Void> adminUpdateCourse(@PathVariable("id") Long couId, Course course){
        adminService.adminUpdateCourse(couId, course);
        return ResponseEntity.ok().build();
    }
    // 등록된 강의 삭제
    @DeleteMapping("/list/courseDelete/{id}")
    public ResponseEntity<Void> adminDeleteCourse(@PathVariable("id") Long couId){
        adminService.adminDeleteCourse(couId);
        return ResponseEntity.noContent().build();
    }


}
