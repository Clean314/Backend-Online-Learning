package com.docker.backend.service.lecture;

import com.docker.backend.dto.Lecture.LectureDTO;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.lecture.LectureHistory;
import com.docker.backend.repository.lecture.LectureHistoryRepository;
import com.docker.backend.repository.lecture.LectureRepository;
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
   private final LectureHistoryRepository lectureHistoryRepository;

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
        for(LectureDTO lecture : dto){
            boolean isTitleDuplicate = (lecture.getLectureId() != null)
                    ? lectureRepository.existsByTitleAndCourseAndIdNot(lecture.getTitle(), courses, lecture.getLectureId())
                    : lectureRepository.existsByTitleAndCourse(lecture.getTitle(), courses);

            boolean isUrlDuplicate = (lecture.getLectureId() != null)
                    ? lectureRepository.existsByVideoUrlAndCourseAndIdNot(lecture.getVideoUrl(), courses, lecture.getLectureId())
                    : lectureRepository.existsByVideoUrlAndCourse(lecture.getVideoUrl(), courses);

            String error = "";
            if (isTitleDuplicate && isUrlDuplicate) error = "둘다";
            else if (isTitleDuplicate) error = "제목";
            else if (isUrlDuplicate) error = "주소";

            switch (error){
                case "둘다" -> throw new IllegalStateException("중복된 내용입니다. 제목: " + lecture.getTitle() + ", 주소: " + lecture.getVideoUrl());
                case "제목" -> throw new IllegalStateException("이미 등록된 강의 제목입니다.");
                case "주소" -> throw new IllegalStateException("이미 등록된 강의 주소입니다.");
            }
             //ID가 없는 건 새로 등록
            if (lecture.getLectureId() == null) {
                Lecture newLecture = new Lecture();
                newLecture.setTitle(lecture.getTitle().trim());
                newLecture.setVideoUrl(lecture.getVideoUrl().trim());
                newLecture.setCourse(courses);
                lectureRepository.save(newLecture);
            // 기존 강의 수정
            } else {
                Lecture existingLecture = lectureRepository.findById(lecture.getLectureId())
                        .orElseThrow(() -> new EntityNotFoundException("해당 강의가 존재하지 않습니다."));
                System.out.println("요청 courseId = " + courseId);
                System.out.println("lecture " + lecture.getLectureId() + " 의 실제 courseId = " + existingLecture.getCourse().getId());
                if (!existingLecture.getCourse().getId().equals(courseId)) {
                    throw new IllegalStateException("강의 ID와 강의 번호가 일치하지 않습니다.");
                }
                if (lecture.getTitle() != null) {
                    existingLecture.setTitle(lecture.getTitle().trim());
                }
                if (lecture.getVideoUrl() != null) {
                    existingLecture.setVideoUrl(lecture.getVideoUrl().trim());
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
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("해당하는 강의가 없습니다."));
        Lecture lecture = lectureRepository.findByCourseAndId(course, lectureId)
                .orElseThrow(() -> new EntityNotFoundException("해당 영상(" + lectureId + ")이 존재하지 않습니다."));
        List<LectureHistory> historyList = lectureHistoryRepository.findAllByLectureId(lectureId);
        double history = historyList.stream().mapToDouble(LectureHistory::getWatchedTime).sum();

        LectureDTO dto = new LectureDTO(lecture);
        dto.setWatchedTime(history);
        return dto;
    }
}
