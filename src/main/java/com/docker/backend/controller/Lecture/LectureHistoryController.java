package com.docker.backend.controller.Lecture;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Student;
import com.docker.backend.service.lecture.LectureHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@PreAuthorize("hasRole('STUDENT')")
public class LectureHistoryController {

    private final LectureHistoryService lectureHistoryService;
    private final AuthUtil authUtil;

    @PostMapping("/time-line")
    public ResponseEntity<String> saveTimeLine(Authentication authentication, @RequestBody LectureHistory dto){
        Student student = authUtil.getStudent(authentication);
        try{
            lectureHistoryService.saveTimeLine(student, dto);
            return ResponseEntity.ok().body("TimeLine Update Success");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
