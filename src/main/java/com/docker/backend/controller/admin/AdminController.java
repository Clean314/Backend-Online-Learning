package com.docker.backend.controller.admin;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.AdminMemberDTO;
import com.docker.backend.dto.CourseDTO;
import com.docker.backend.service.admin.AdminService;
import com.docker.backend.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final CourseService courseService;
    private final AuthUtil authUtil;


    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> adminDashBoard() {
        Map<String, Object> result = new HashMap<>();
        result.put("members", adminService.getAllMembers());
        result.put("courses", courseService.getAllCourses());
        result.put("totalMember", adminService.getAllMembers().stream().count());
        result.put("totalCourses", courseService.getAllCourses().stream().count());

        return ResponseEntity.ok(result);
    }
}
