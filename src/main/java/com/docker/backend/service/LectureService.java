package com.docker.backend.service;

import com.docker.backend.dto.CourseDTO;
import com.docker.backend.dto.LectureDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Lecture;
import com.docker.backend.repository.LectureRepository;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    // 동영상 저장
    public void createLecture(List<LectureDTO> lectures, Long couId) {
        Course courses = courseRepository.findById(couId)
                .orElseThrow(()-> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));

        for(LectureDTO dto : lectures) {
            Lecture lecture = Lecture.builder()
                    .title(dto.getTitle())
                    .videoUrl(dto.getVideoUrl())
                    .courseId(courses)
                    .build();
            lectureRepository.save(lecture);
        }
    }
}
