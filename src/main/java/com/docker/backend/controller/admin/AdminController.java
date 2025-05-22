package com.docker.backend.controller.admin;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.AdminMemberDTO;
import com.docker.backend.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final AuthUtil authUtil;


    @GetMapping("/list")
    public ResponseEntity<List<AdminMemberDTO>> SingupMembers(){
        return ResponseEntity.ok(adminService.getAllMembers());   // 소희 코드 고쳐야해서 description에 날짜가 담김,,
    }
}
