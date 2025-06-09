package com.docker.backend.service.exam;

import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.Exam;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.enums.ExamStatus;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.exam.ExamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;

    // 시험 목록 조회 (해당 강의가 교수자 소유인지 확인)
    public List<ExamDTO> getExamsByCourse(Long courseId, Long educatorId) {
        verifyCourseOwnership(courseId, educatorId);

        List<Exam> exams = examRepository.findByCourseIdAndCourse_Educator_Id(courseId, educatorId);
        return exams.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // 특정 시험 단건 조회
    public ExamDTO getExamByIdAndCourse(Long courseId, Long educatorId, Long examId) {
        verifyCourseOwnership(courseId, educatorId);
        Exam exam = examRepository.findByCourseIdAndId(courseId, examId);
        if (exam == null) {
            throw new EntityNotFoundException("해당 강의에 대한 시험이 없습니다.");
        }
        return toDTO(exam);
    }

    // 시험 생성
    public ExamDTO createExam(Long courseId, Educator educator, ExamCreateDTO dto) {
        Course course = courseRepository.findByEducatorAndId(educator, courseId)
                .orElseThrow(() -> new AccessDeniedException("해당 강의에 대한 접근 권한이 없습니다."));

        validateExamSchedule(dto.getStartTime(), dto.getEndTime());

        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setStatus(ExamStatus.PREPARING);

        return toDTO(examRepository.save(exam));
    }

    // 시험 수정
    public ExamDTO updateExam(Long examId, Long educatorId, ExamUpdateDTO dto) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("시험을 찾을 수 없습니다."));

        if (!exam.getCourse().getEducator().getId().equals(educatorId)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        if (Duration.between(LocalDateTime.now(), exam.getStartTime()).toHours() < 24) {
            throw new IllegalArgumentException("시험 시작 24시간 전까지만 수정할 수 있습니다.");
        }

        if (dto.getStatus() != ExamStatus.CANCELLED) {
            throw new IllegalArgumentException("시험 상태는 'CANCELLED'로만 변경할 수 있습니다.");
        }

        validateExamSchedule(dto.getStartTime(), dto.getEndTime());

        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStatus(dto.getStatus());

        return toDTO(examRepository.save(exam));
    }

    // 시험 삭제
    public void deleteExam(Long examId, Long educatorId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new EntityNotFoundException("시험을 찾을 수 없습니다."));

        if (!exam.getCourse().getEducator().getId().equals(educatorId)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        examRepository.delete(exam);
    }

    // ===== 공통 메서드 =====

    private void verifyCourseOwnership(Long courseId, Long educatorId) {
        boolean ownsCourse = courseRepository.existsByIdAndEducator_Id(courseId, educatorId);
        if (!ownsCourse) {
            throw new AccessDeniedException("강의 접근 권한 없음");
        }
    }

    private void validateExamSchedule(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("시험 종료 시간이 시작 시간보다 빠를 수 없습니다.");
        }
        if (Duration.between(LocalDateTime.now(), start).toHours() < 24) {
            throw new IllegalArgumentException("시험 시작 시간은 현재 시간보다 최소 24시간 이후여야 합니다.");
        }
    }

    // ExamDTO 변환
    private ExamDTO toDTO(Exam exam) {
        return new ExamDTO(exam);
    }


}
