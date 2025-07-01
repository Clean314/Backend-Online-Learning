package com.docker.backend.controller.Lecture;

import com.docker.backend.domain.user.Student;
import com.docker.backend.dto.Lecture.LectureDTO;
import com.docker.backend.service.lecture.LectureService;
import com.docker.backend.config.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@PreAuthorize("hasRole('EDUCATOR')")
public class LectureController {

    private final LectureService lectureService;
    private final AuthUtil authUtil;

    // 강의(동영상)등록
    @PostMapping("/create/url/{id}")
    public ResponseEntity<String> createLecture(@RequestBody @Valid List<LectureDTO> lecture, @PathVariable("id") Long courseId){
        try{
            lectureService.createLecture(lecture, courseId);
            return ResponseEntity.ok("강의등록완료");
        }catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("오류가 발생하였습니다.");
        }
    }
//    // 강의 영상 리스트
//    @GetMapping("/list/{courseId}")
//    public ResponseEntity<List<LectureDTO>> getLectureList(Authentication authentication, @PathVariable("courseId") Long courseId){
//        // 관리자는 학생이 아니므로, 예시로 null을 넘김 (필요시 로직 보완)
//        Student student = null;
//        return ResponseEntity.ok(lectureService.getLectureList(courseId, student));
//    }

    // 강의(동영상) 수정
    @PatchMapping("/list/{courseId}/lecture")
    public ResponseEntity<String> updateLecture(@PathVariable("courseId") Long courseId, @RequestBody @Valid List<LectureDTO> dto){
        try {
            lectureService.updateLecture(courseId, dto);
            return ResponseEntity.ok().body("업데이트 완료"); // 정상 처리
        // 400 Bad Request로 에러 메시지 반환
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    // 강의(동영상) 삭제
    @DeleteMapping("/list/{courseId}/{lectureId}")
    public ResponseEntity<String> deleteLecture(@PathVariable Long courseId,
                                           @PathVariable Long lectureId) {
        lectureService.deleteLecture(courseId, lectureId);
        return ResponseEntity.ok("삭제 완료");
    }
}
