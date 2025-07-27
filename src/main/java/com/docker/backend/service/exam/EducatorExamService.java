 package com.docker.backend.service.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.user.Student;
import com.docker.backend.dto.exam.*;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.exam.EducatorExamMapper;
import com.docker.backend.mapper.exam.ExamSubmissionMapper;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.StudentAnswerRepository;
import com.docker.backend.repository.exam.StudentExamStatusRepository;
import com.docker.backend.service.VerifyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducatorExamService {
    private final VerifyService verifyService;

    private final StudentExamStatusRepository studentExamStatusRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamRepository examRepository;
    private EducatorExamMapper educatorExamMapper;
    private ExamSubmissionMapper submissionMapper;

    public List<EducatorExamDTO> getExamsByCourse(Long educatorId, Long courseId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        return educatorExamMapper.toDtoList(examRepository.findByCourseId(courseId));
    }

    public EducatorExamDTO getExamByIdAndCourse(Long educatorId, Long courseId, Long examId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        return educatorExamMapper.toDto(verifyService.isExistExam(courseId, examId));
    }

    @Transactional
    public EducatorExamDTO createExam(Long educatorId, Long courseId, ExamCreateDTO createDTO) {
        Course course = verifyService.isOwnerOfCourse(educatorId, courseId);
        validateExamDuration(createDTO.getStartTime(), createDTO.getEndTime());

        return educatorExamMapper.toDto(
                examRepository.save(
                    educatorExamMapper.toEntity(createDTO, course)
                ));
    }

    public EducatorExamDTO updateExam(Long educatorId, Long courseId, Long examId, ExamUpdateDTO updateDTO) {
        Course course = verifyService.isOwnerOfCourse(educatorId, courseId);
        validateExamDuration(updateDTO.getStartTime(), updateDTO.getEndTime());
        verifyService.isExistExam(courseId, examId);

        return educatorExamMapper.toDto(
                examRepository.save(
                        educatorExamMapper.toEntity(updateDTO, course, examId)
                ));
    }

    public void deleteExam(Long educatorId, Long courseId, Long examId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        Exam exam = verifyService.isExistExam(courseId, examId);
        examRepository.delete(exam);
    }

    public List<StudentExamSubmissionDTO> getExamSubmissions(Long courseId, Long examId, Long educatorId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        Exam exam = verifyService.isExistExam(courseId, examId);
        List<StudentExamStatus> statuses =
                studentExamStatusRepository.findByExamId(examId);

        return submissionMapper.toDtoList(statuses);
    }

    @Transactional
    public void updateAnswerEvaluation(Long courseId, Long examId, Long educatorId, Long studentId, Long questionId,
                                       AnswerEvaluationUpdateDTO dto) {
        verifyService.isOwnerOfCourse(courseId, educatorId);
        StudentExamStatus status = verifyService.isExistStudentExamStatus(studentId, examId);
        StudentAnswer answer = verifyService.isExistStudentAnswer(status.getId(), questionId);

        answer.setCorrect(dto.isCorrect());
        answer.setScore(dto.getScore());

        List<StudentAnswer> allAnswers = studentAnswerRepository.findByStudentExamStatus(status);
        int newTotalScore = allAnswers.stream().mapToInt(StudentAnswer::getScore).sum();
        status.setTotalScore(newTotalScore);
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
