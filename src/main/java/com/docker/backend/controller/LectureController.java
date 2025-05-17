package com.docker.backend.controller;

import com.docker.backend.config.CustomUserDetails;
import com.docker.backend.entity.Lecture;
import com.docker.backend.entity.Member;
import com.docker.backend.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    // TODO: lecture 가 아니라 Course 로 바꾸기
    // TODO: import.sql에 샘플 데이터 넣어서 권한 분기 테스트
    // TODO: Entity에서 @Data로 변경
    @GetMapping
    public ResponseEntity<List<Lecture>> getAllLecture(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        List<Lecture> lectures = lectureService.getAllLectures(member);
        return ResponseEntity.ok(lectures);
    }

}
