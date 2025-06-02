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

    // 강의(동영상)등록
    @PostMapping("/create/url/{id}")
    public ResponseEntity<String> createVideo(@RequestBody @Valid LectureDTO lecture, @PathVariable("id") Long courseId){
        try{
            lectureService.createLecture(List.of(lecture), courseId);
            return ResponseEntity.ok("강의등록완료");
        }catch (IllegalStateException e){
            return ResponseEntity.badRequest().body("이미 등록된 정보입니다");
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("오류가 발생하였습니다.");
        }
    }
    // 강의 영상
    @GetMapping("/list/{courseId}")
    public ResponseEntity<List<LectureDTO>> getLecture(@PathVariable("courseId") Long courseId){
        return ResponseEntity.ok(lectureService.listLecture(courseId));
    }

    // 강의(동영상) 수정

    // 강의(동영상) 삭제
}
