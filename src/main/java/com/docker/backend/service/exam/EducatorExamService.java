package com.docker.backend.service.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enums.ExamStatus;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.exam.EducatorExamMapper;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EducatorExamService {
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final EducatorExamMapper educatorExamMapper;

    public List<EducatorExamDTO> getExamsByCourse(Long educatorId, Long courseId) {
        isOwnerOfCourse(educatorId, courseId);
        return examRepository.findByCourseId(courseId)
                .stream().map(EducatorExamDTO::of).toList();
    }

    public EducatorExamDTO getExamByIdAndCourse(Long educatorId, Long courseId, Long examId) {
        isOwnerOfCourse(educatorId, courseId);
        return educatorExamMapper.toDto(
                examRepository.findByCourseIdAndId(courseId, educatorId)
        );
    }

    public EducatorExamDTO createExam(Long educatorId, Long courseId, ExamCreateDTO dto) {
        Course course = isOwnerOfCourse(educatorId, courseId);
        validateExamDuration(dto.getStartTime(), dto.getEndTime());

        return educatorExamMapper.toDto(
                educatorExamMapper.toEntity(dto, courseId, ExamStatus.PREPARING)
        );
    }

    public EducatorExamDTO updateExam(Long educatorId, Long courseId, Long examId, ExamUpdateDTO dto) {
        isOwnerOfCourse(educatorId, courseId);
        validateExamDuration(dto.getStartTime(), dto.getEndTime());
        isExistExam(courseId, examId);

        Exam exam = educatorExamMapper
                .toEntity(dto, courseId, examId);

        return educatorExamMapper.toDto(examRepository.save(exam));
    }

    public void deleteExam(Long educatorId, Long courseId, Long examId) {
        isOwnerOfCourse(educatorId, courseId);
        Exam exam = isExistExam(courseId, examId);
        examRepository.delete(exam);
    }

    private Course isOwnerOfCourse(Long educatorId, Long courseId) {
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

    private void validateExamDuration(LocalDateTime startTime, LocalDateTime endTime) {
        if (Duration.between(LocalDateTime.now(), startTime).toHours() < 24) {
            throw new GlobalExceptionHandler.UpdateLimitExamException();
        }

        if (Duration.between(startTime, endTime).toMinutes() < 20) {
            throw new GlobalExceptionHandler.InvalidExamDurationException(
                    startTime.toString(),
                    endTime.toString());
        }
    }



}
