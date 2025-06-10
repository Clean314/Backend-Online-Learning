package com.docker.backend.service.exam;

import com.docker.backend.dto.exam.*;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Exam;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.ExamStatus;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<ExamDTO> getEducatorExamsByCourse(Long courseId, Long educatorId) {
        verifyCourseOwnership(courseId, educatorId);
        return examRepository.findByCourseIdAndCourse_Educator_Id(courseId, educatorId)
                .stream().map(ExamDTO::of).toList();
    }

    public List<ExamDTO> getStudentExamsByCourse(Long courseId, Long studentId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 강의에 대한 수강 등록이 없습니다."));

        return examRepository.findByCourseId(studentId)
                .stream().map(ExamDTO::of).toList();
    }

    public ExamDTO getExamByIdAndCourse(Long courseId, Long educatorId, Long examId) {
        courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new AccessDeniedException("강의에 대한 접근 권한이 없습니다."));

        examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        return ExamDTO.of(examRepository.findByCourseIdAndId(courseId, examId));
    }

    public ExamDTO createExam(Long courseId, Long educatorId, ExamCreateDTO dto) {
        Course course = courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new AccessDeniedException("강의에 대한 접근 권한이 없습니다."));
        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());
        Exam exam = ExamMapper.toEntity(dto, course);
        return ExamDTO.of(examRepository.save(exam));
    }

    public ExamDTO updateExam(Long examId, Long educatorId, ExamUpdateDTO dto) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        if (!exam.getCourse().getEducator().getId().equals(educatorId)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new IllegalStateException("시험 시작 24시간 전까지만 수정할 수 있습니다.");
        }

        if (dto.getStatus() != ExamStatus.CANCELLED) {
            throw new IllegalArgumentException("시험 상태는 'CANCELLED' 로만 변경할 수 있습니다.");
        }

        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());

        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStatus(dto.getStatus());
        return ExamDTO.of(examRepository.save(exam));
    }

    private void verifyCourseOwnership(Long courseId, Long educatorId) {
        if (!courseRepository.existsByIdAndEducator_Id(courseId, educatorId)) {
            throw new AccessDeniedException("강의 접근 권한 없음");
        }
    }
}

