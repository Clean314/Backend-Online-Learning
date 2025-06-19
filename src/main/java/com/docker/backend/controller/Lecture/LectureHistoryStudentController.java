package com.docker.backend.controller.Lecture;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.Lecture.AttendanceDTO;
import com.docker.backend.dto.course.CourseAttendanceDTO;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.entity.user.Student;
import com.docker.backend.service.lecture.LectureHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.docker.backend.dto.Lecture.LectureHistoryRequestDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@PreAuthorize("hasRole('STUDENT')")
public class LectureHistoryStudentController {

    private final LectureHistoryService lectureHistoryService;
    private final AuthUtil authUtil;

    @PostMapping("/time-line")
    public ResponseEntity<String> saveTimeLine(Authentication authentication, @RequestBody LectureHistoryRequestDTO dto){
        Student student = authUtil.getStudent(authentication);
        try{
            lectureHistoryService.saveTimeLine(student, dto);
            return ResponseEntity.ok().body("TimeLine Update Success");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/attendance-avg/{courseId}")
    public ResponseEntity<?> getStudentAttendance(@PathVariable Long courseId){

        try {
            return ResponseEntity.ok().body(lectureHistoryService.getStudentAttendance(courseId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/attendance/student/{lectureId}")
    public ResponseEntity<AttendanceDTO> attendance(@PathVariable Long lectureId){
        return ResponseEntity.ok(lectureHistoryService.attendance(lectureId));
    }

    @GetMapping("/watched-time/{lectureId}")
    public ResponseEntity<Double> getWatchedTime(Authentication authentication, @PathVariable Long lectureId) {
        Student student = authUtil.getStudent(authentication);
        double watchedTime = lectureHistoryService.getWatchedTime(student, lectureId);
        return ResponseEntity.ok(watchedTime);
    }
}
