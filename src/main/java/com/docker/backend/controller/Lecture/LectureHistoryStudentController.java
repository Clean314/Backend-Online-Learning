package com.docker.backend.controller.Lecture;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.domain.lecture.LectureHistory;
import com.docker.backend.domain.user.Student;
import com.docker.backend.service.lecture.LectureHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@PreAuthorize("hasRole('STUDENT')")
public class LectureHistoryStudentController {

    private final LectureHistoryService lectureHistoryService;
    private final AuthUtil authUtil;

//    @PostMapping("/time-line")
//    public ResponseEntity<String> saveTimeLine(Authentication authentication, @RequestBody LectureHistory dto){
//        Student student = authUtil.getStudent(authentication);
//        try{
//            lectureHistoryService.saveTimeLine(student, dto);
//            return ResponseEntity.ok().body("TimeLine Update Success");
//        }catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @GetMapping("/attendance-avg/{courseId}")
//    public ResponseEntity<?> avgAttendance(@PathVariable Long courseId){
//
//        try {
//            return ResponseEntity.ok().body(lectureHistoryService.avgAttendance(courseId));
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
