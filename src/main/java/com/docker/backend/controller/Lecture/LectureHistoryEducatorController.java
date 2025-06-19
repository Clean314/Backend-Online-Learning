package com.docker.backend.controller.Lecture;

import com.docker.backend.dto.Lecture.AttendanceDTO;
import com.docker.backend.dto.course.CourseAttendanceDTO;
import com.docker.backend.service.lecture.LectureHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/history")
@PreAuthorize("hasRole('EDUCATOR')")
public class LectureHistoryEducatorController {

    private final LectureHistoryService lectureHistoryService;

    @GetMapping("/attendance/{courseId}")
    public ResponseEntity<List<CourseAttendanceDTO>> getStudentAttendance(@PathVariable Long courseId){
        return ResponseEntity.ok(lectureHistoryService.getStudentAttendance(courseId));
    }

    @GetMapping("/attendance/{courseId}/lsit")
    public ResponseEntity<List<AttendanceDTO>> attendanceList(@PathVariable Long courseId){
        return ResponseEntity.ok(lectureHistoryService.attendanceList(courseId));
    }
}
