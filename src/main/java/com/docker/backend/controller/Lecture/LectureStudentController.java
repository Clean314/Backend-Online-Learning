package com.docker.backend.controller.Lecture;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.dto.Lecture.LectureDTO;
import com.docker.backend.service.lecture.LectureService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student/lecture")
@PreAuthorize("hasRole('STUDENT')")
public class LectureStudentController {

    private final LectureService lectureService;
    private final AuthUtil authUtil;

    // 강의 영상 리스트
    @GetMapping("/list/{courseId}")
    public ResponseEntity<List<LectureDTO>> getLectureList(@PathVariable("courseId") Long courseId){
        return ResponseEntity.ok(lectureService.getLectureList(courseId));
    }

    // 강의실행
    @GetMapping("/view/{courseId}/{lectureId}")
    public ResponseEntity<?> viewVideo(@PathVariable("courseId") Long courseId, @PathVariable("lectureId") Long lectureId){
        try {
            return ResponseEntity.ok(lectureService.viewVideo(courseId, lectureId));
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
