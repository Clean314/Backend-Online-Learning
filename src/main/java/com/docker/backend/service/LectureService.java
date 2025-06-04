package com.docker.backend.service;

import com.docker.backend.dto.LectureDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Lecture;
import com.docker.backend.repository.LectureRepository;
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

    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    // 동영상 저장
    public void createLecture(List<LectureDTO> lectures, Long couId) {
        Course courses = courseRepository.findById(couId)
                .orElseThrow(()-> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));
        for(LectureDTO dto : lectures) {
            if(lectureRepository.existsByTitleAndCourse(dto.getTitle(), courses)){
                throw new IllegalStateException("이미 등록된 강의 제목입니다." + dto.getTitle());
            }
            if(lectureRepository.existsByVideoUrlAndCourse(dto.getVideoUrl(), courses)){
                throw new IllegalStateException("이미 등록된 URL입니다." + dto.getVideoUrl());
            }
            Lecture lecture = new Lecture();
                lecture.setTitle(dto.getTitle());
                lecture.setVideoUrl(dto.getVideoUrl());
                lecture.setCourse(courses);
            lectureRepository.save(lecture);
        }
    }

    // 강의 영상 리스트
    public List<LectureDTO> getLectureList(Long courseId){
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
        Course courses = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 강의를 찾을 수 없습니다."));
        for (LectureDTO lecture : dto) {
            if(lectureRepository.existsByTitleAndCourse(lecture.getTitle(), courses)){
                throw new IllegalStateException("이미 등록된 강의 제목입니다." + lecture.getTitle());
            }
            if(lectureRepository.existsByVideoUrlAndCourse(lecture.getVideoUrl(), courses)){
                throw new IllegalStateException("이미 등록된 URL입니다." + lecture.getVideoUrl());
            }
            // ID가 없는 건 새로 등록
            if (lecture.getLectureId() == null) {
                Lecture newLecture = new Lecture();
                newLecture.setTitle(lecture.getTitle());
                newLecture.setVideoUrl(lecture.getVideoUrl());
                newLecture.setCourse(courses);
                lectureRepository.save(newLecture);
            // 기존 강의 수정
            } else {
                Lecture existingLecture = lectureRepository.findById(lecture.getLectureId())
                        .orElseThrow(() -> new EntityNotFoundException("해당 강의가 존재하지 않습니다."));
                if (!existingLecture.getCourse().getId().equals(courseId)) {
                    throw new IllegalStateException("강의 ID와 강의 번호가 일치하지 않습니다.");
                }
                if (lecture.getTitle() != null) {
                    existingLecture.setTitle(lecture.getTitle());
                }
                if (lecture.getVideoUrl() != null) {
                    existingLecture.setVideoUrl(lecture.getVideoUrl());
                }
                lectureRepository.save(existingLecture);
            }
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
    public LectureDTO viewVideo(Long courseId, Long lectureId){
        System.out.println("요청 들어옴: courseId=" + courseId + ", lectureId=" + lectureId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("해당하는 강의가 없습니다."));
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new EntityNotFoundException("해당 영상(" + lectureId + ")이 존재하지 않습니다."));
        return new LectureDTO(lecture);
    }
}
