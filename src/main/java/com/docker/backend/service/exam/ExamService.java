package com.docker.backend.service.exam;

import com.docker.backend.dto.exam.*;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.exam.Exam;
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

    public List<EducatorExamDTO> getEducatorExamsByCourse(Long courseId, Long educatorId) {
        verifyCourseOwnership(courseId, educatorId);
        return examRepository.findByCourseIdAndCourse_Educator_Id(courseId, educatorId)
                .stream().map(EducatorExamDTO::of).toList();
    }

    public List<StudentExamDTO> getStudentExamsByCourse(Long courseId, Long studentId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 강의에 대한 수강 등록이 없습니다."));

        return examRepository.findByCourseId(studentId)
                .stream().map(StudentExamDTO::of).toList();
    }

    public EducatorExamDTO getExamByIdAndCourse(Long courseId, Long educatorId, Long examId) {
        courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));

        examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        return EducatorExamDTO.of(examRepository.findByCourseIdAndId(courseId, examId));
    }

    public StudentExamDTO getStudentExamByIdAndCourse(Long courseId, Long educatorId, Long examId) {
        courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));

        examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        return StudentExamDTO.of(examRepository.findByCourseIdAndId(courseId, examId));
    }

    public EducatorExamDTO createExam(Long courseId, Long educatorId, ExamCreateDTO dto) {
        Course course = courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));
        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());

        Exam exam = ExamMapper.toEntity(dto, course);
        exam.setStatus(ExamStatus.PREPARING);
        Exam saved = examRepository.save(exam);
        return EducatorExamDTO.of(saved);
    }

    public EducatorExamDTO updateExam(Long examId, Long educatorId, ExamUpdateDTO dto) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        if (!exam.getCourse().getEducator().getId().equals(educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("수정 권한이 없습니다.");
        }

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new GlobalExceptionHandler.UpdateLimitExamException("시험 시작 24시간 전까지만 수정할 수 있습니다.");
        }

        if (dto.getStatus() != ExamStatus.CANCELLED) {
            throw new GlobalExceptionHandler.InvalidExamStateException(exam.getStatus().name(), dto.getStatus().name());
        }

        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());

        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStatus(dto.getStatus());
        return EducatorExamDTO.of(examRepository.save(exam));
    }

    public void deleteExam(Long examId, Long educatorId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다."));

        if (!exam.getCourse().getEducator().getId().equals(educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("삭제 권한이 없습니다.");
        }

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new GlobalExceptionHandler.UpdateLimitExamException("시험 시작 24시간 전까지만 삭제할 수 있습니다.");
        }

        examRepository.delete(exam);
    }

    private void verifyCourseOwnership(Long courseId, Long educatorId) {
        if (!courseRepository.existsByIdAndEducator_Id(courseId, educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("강의 접근 권한이 없습니다.");
        }
    }
}

