package com.docker.backend.controller.Lecture;

import com.docker.backend.dto.LectureDTO;
import com.docker.backend.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@PreAuthorize("hasRole('EDUCATOR')")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/create/url/{id}")
    public ResponseEntity<String> createVideo(@RequestBody @Valid List<LectureDTO> lecture, @PathVariable("id") Long courseId){
        lectureService.createLecture(lecture, courseId);
        return ResponseEntity.ok("강의등록완료");
    }
}
