package com.docker.backend.service.testScore;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.Exam;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.testScore.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;


//    // 시험 목록 조회 (교수자가 자신이 소유한 강의에 대해서만)
//    public List<Exam> getExamsByCourse(Long courseId, Long instructorId) {
//        return examRepository.findByCourseIdAndCourseInstructorId(courseId, instructorId);
//    }

//    // 시험 생성
//    public Exam createExam(Long courseId, Educator educator, Exam exam) {
//        Course course = courseRepository.findByEducatorAndId(educator, courseId)
//                .orElseThrow(() -> new AccessDeniedException("강의 접근 권한 없음"));
//
//        Exam exam = new Exam();
//        exam.setTitle(dto.getTitle());
//        exam.setStartTime(dto.getStartTime());
//        exam.setEndTime(dto.getEndTime());
//        exam.setCourse(course);
//
//        return examRepository.save(exam);
//    }
//
//    // 시험 수정
//    public Exam updateExam(Long examId, Long instructorId, ExamUpdateRequest dto) {
//        Exam exam = examRepository.findById(examId)
//                .orElseThrow(() -> new EntityNotFoundException("시험 없음"));
//
//        // 권한 확인
//        if (!exam.getCourse().getInstructor().getId().equals(instructorId)) {
//            throw new AccessDeniedException("수정 권한 없음");
//        }
//
//        exam.setTitle(dto.getTitle());
//        exam.setStartTime(dto.getStartTime());
//        exam.setEndTime(dto.getEndTime());
//
//        return examRepository.save(exam);
//    }

}