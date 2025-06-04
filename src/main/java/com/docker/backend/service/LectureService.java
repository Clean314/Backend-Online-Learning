package com.docker.backend.service;

import com.docker.backend.dto.LectureDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Lecture;
import com.docker.backend.repository.LectureRepository;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    // 동영상 저장
    public void createLecture(List<LectureDTO> lectures, Long couId) {
        Course courses = courseRepository.findById(couId)
                .orElseThrow(()-> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));

        for(LectureDTO dto : lectures) {
            boolean sameTitle = lectureRepository.existsByTitleAndCourse(dto.getTitle(), courses);
            if(sameTitle){
                throw new IllegalStateException("이미 등록된 강의 제목입니다." + dto.getTitle());
            }
            boolean sameVideoUrl = lectureRepository.existsByVideoUrlAndCourse(dto.getVideoUrl(), courses);
            if(sameVideoUrl){
                throw new IllegalStateException("이미 등록된 URL입니다." + dto.getVideoUrl());
            }
            Lecture lecture = new Lecture();
                lecture.setTitle(dto.getTitle());
                lecture.setVideoUrl(dto.getVideoUrl());
                lecture.setCourse(courses);
            lectureRepository.save(lecture);
        }
    }

    // 강의 영상
    public List<LectureDTO> getLecture(Long courseId){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return lectureRepository.findByCourseId(courseId).stream()
                .map(lectures -> {
                    LectureDTO lecture = new LectureDTO(lectures);
                    LocalDateTime date = lectures.getUpdatedAt();
                    if (date == null) {
                        date = lectures.getCreatedAt();
                    }
                    lecture.setTitle(lectures.getTitle());
                    lecture.setVideoUrl(lectures.getVideoUrl());
                    lecture.setUpdatedAt(date.format(formatter));
                    return lecture;
                })
                .toList();
    }

    public void updateLecture(Long courseId, List<LectureDTO> dto){
        for(LectureDTO lectures : dto){
            Lecture lecture = lectureRepository.findById(lectures.getLectureId())
                    .orElseThrow(()-> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));
            if(!lecture.getCourse().getId().equals(courseId)){
                throw new IllegalArgumentException("해당 강의는 강의번호와 일치하지 않습니다.");
            }
            lecture.setTitle(lectures.getTitle());
            lecture.setVideoUrl(lectures.getVideoUrl());
        }
    }
    public void deleteLecture(Long courseId, Long lectureId){
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(()-> new EntityNotFoundException("해당하는 강의가 없습니다."));
        if(!lecture.getCourse().getId().equals(courseId)){
            throw new IllegalArgumentException("강의번호가 일치하지 않습니다.");
        }
        lectureRepository.delete(lecture);
    }


}
