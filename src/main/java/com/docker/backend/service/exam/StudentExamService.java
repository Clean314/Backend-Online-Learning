package com.docker.backend.service.exam;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.exam.EducatorExamMapper;
import com.docker.backend.mapper.exam.StudentExamMapper;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentExamService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final StudentExamMapper studentExamMapper;

    public List<StudentExamDTO> getExamsByCourse(Long courseId, Long studentId) {
        isEnrolledInCourse(courseId, studentId);
        return studentExamMapper.toDtoList(examRepository.findByCourseId(courseId));
    }

    public StudentExamDTO getExamByIdAndCourse(Long courseId, Long examId, Long studentId) {
        isEnrolledInCourse(courseId, studentId);
        return studentExamMapper.toDto(isExistExam(courseId, examId));
    }




    private void isEnrolledInCourse(Long courseId, Long studentId) {
        if (!enrollmentRepository.ExistsByStudentIdAndCourseId(courseId, studentId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("해당 강의에 대한 수강 정보가 없습니다.");
        }
    }

    private Exam isExistExam(Long courseId, Long examId) {
        Exam exam = examRepository.findByCourseIdAndId(courseId, examId);
        if (exam == null) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
        return exam;
    }
}
