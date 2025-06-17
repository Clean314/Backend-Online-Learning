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

    public EducatorExamDTO getEducatorExamByIdAndCourse(Long educatorId, Long courseId, Long examId) {
        verifyCourseOwnership(courseId, educatorId);
        return EducatorExamDTO.of(isExistExam(courseId, examId));
    }

    public EducatorExamDTO createExam(Long courseId, Long educatorId, ExamCreateDTO dto) {
        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());

        Exam exam = ExamMapper.toEntity(dto, verifyCourseOwnership(courseId, educatorId));
        exam.setStatus(ExamStatus.PREPARING);

        Exam saved = examRepository.save(exam);
        return EducatorExamDTO.of(saved);
    }

    public EducatorExamDTO updateExam(Long courseId, Long examId, Long educatorId, ExamUpdateDTO dto) {
        verifyCourseOwnership(courseId, educatorId);
        Exam exam = isExistExam(courseId, examId);

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new GlobalExceptionHandler.UpdateLimitExamException("시험 시작 24시간 전까지만 수정할 수 있습니다.");
        }

        ExamValidator.validateSchedule(dto.getStartTime(), dto.getEndTime());

        if (!exam.getStatus().equals(dto.getStatus())) {
            exam.getStatus().validateTransition(dto.getStatus());
            exam.setStatus(dto.getStatus());
        }

        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());

        return EducatorExamDTO.of(examRepository.save(exam));
    }


    public void deleteExam(Long courseId, Long examId, Long educatorId) {
        verifyCourseOwnership(educatorId, courseId);
        Exam exam = isExistExam(courseId, examId);

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new GlobalExceptionHandler.UpdateLimitExamException("시험 시작 24시간 전까지만 삭제할 수 있습니다.");
        }

        examRepository.delete(exam);
    }

    public List<StudentExamDTO> getStudentExamsByCourse(Long courseId, Long studentId) {
        verifyEnrollCourse(courseId, studentId);
        return examRepository.findByCourseId(studentId)
                .stream().map(StudentExamDTO::of).toList();
    }

    public StudentExamDTO getStudentExamByIdAndCourse(Long courseId, Long studentId, Long examId) {
        verifyEnrollCourse(courseId, studentId);
        Exam exam = isExistExam(courseId, examId);
        return StudentExamDTO.of(exam);
    }

    private void verifyEnrollCourse(Long courseId, Long studentId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 강의에 대한 수강 등록이 없습니다."));
    }

    private Course verifyCourseOwnership(Long courseId, Long educatorId) {
        return courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));
    }

    private Exam isExistExam(Long courseId, Long examId) {
        Exam exam = examRepository.findByCourseIdAndId(courseId, examId);
        if (exam == null) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
        return exam;
    }

    private void scoreUpdate(){

    }
}

