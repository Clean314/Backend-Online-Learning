package com.docker.backend.service.exam;

import com.docker.backend.domain.enums.QuestionType;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.user.Student;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.exam.StudentExamMapper;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.StudentAnswerRepository;
import com.docker.backend.repository.exam.StudentExamStatusRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import com.docker.backend.repository.member.MemberRepository;
import com.docker.backend.service.VerifyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentExamService {
    private final VerifyService verifyService;

    private final EnrollmentRepository enrollmentRepository;
    private final ExamRepository examRepository;
    private final MemberRepository memberRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final StudentExamStatusRepository studentExamStatusRepository;
    private final QuestionRepository questionRepository;
    private final StudentExamMapper studentExamMapper;

    public List<StudentExamDTO> getExamsByCourse(Long courseId, Long studentId) {
        verifyService.isEnrolled(courseId, studentId);
        return studentExamMapper.toDtoList(examRepository.findByCourseId(courseId));
    }

    public StudentExamDTO getExamByIdAndCourse(Long courseId, Long examId, Long studentId) {
        verifyService.isEnrolled(courseId, studentId);
        return studentExamMapper.toDto(verifyService.isExistExam(courseId, examId));
    }

    public void initializeExamStatus(Long courseId, Long examId, Long studentId) {
        verifyService.isEnrolled(courseId, studentId);
        Exam exam = verifyService.isExistExam(courseId, examId);
        Student student = verifyService.isExistStudent(studentId);

        studentExamStatusRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseGet(() -> {
                    StudentExamStatus status = new StudentExamStatus();
                    status.setStudent(student);
                    status.setExam(exam);
                    status.setSubmitted(false);
                    status.setTotalScore(0);
                    return studentExamStatusRepository.save(status);
                });
    }

    @Transactional
    public void saveStudentAnswers(Long courseId, Long examId, Long studentId, Map<Long, String> answers) {
        Exam exam = verifyService.isExistExam(courseId, examId);
        validateExamPeriod(exam.getStartTime(), exam.getEndTime());
        StudentExamStatus status = getOrCreateStudentExamStatus(studentId, examId, false);

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Question question = questionRepository.findById(entry.getKey())
                    .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));
            StudentAnswer answer = findOrCreateAnswer(status, question);
            answer.setAnswer(entry.getValue());
            answer.setCorrect(false);
            answer.setScore(0);
        }
    }

    @Transactional
    public int submitExam(Long courseId, Long examId, Long studentId, Map<Long, String> answers) {
        Exam exam = verifyService.isExistExam(courseId, examId);
        validateExamPeriod(exam.getStartTime(), exam.getEndTime());
        StudentExamStatus status = getOrCreateStudentExamStatus(studentId, examId, true);
        int totalScore = 0;

        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            Question question = questionRepository.findById(entry.getKey())
                    .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));
            StudentAnswer answer = findOrCreateAnswer(status, question);
            answer.setAnswer(entry.getValue());

            if (question.getQuestionType() == QuestionType.CHOICE) {
                List<String> choices = question.getChoices();
                try {
                    int choiceIndex = Integer.parseInt(entry.getValue());
                    if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                        String selectedChoice = choices.get(choiceIndex);
                        boolean isCorrect = question.getAnswer().equalsIgnoreCase(selectedChoice);
                        answer.setCorrect(isCorrect);
                        int score = isCorrect ? question.getScore() : 0;
                        answer.setScore(score);
                        totalScore += score;
                    } else {
                        answer.setCorrect(false);
                        answer.setScore(0);
                    }
                } catch (NumberFormatException e) {
                    answer.setCorrect(false);
                    answer.setScore(0);
                }
            } else if (question.getQuestionType() == QuestionType.TRUE_FALSE) {
                List<String> choices = question.getChoices();
                try {
                    int choiceIndex = Integer.parseInt(entry.getValue());
                    if (choiceIndex >= 0 && choiceIndex < choices.size()) {
                        String selectedChoice = choices.get(choiceIndex);
                        boolean isCorrect = question.getAnswer().equalsIgnoreCase(selectedChoice);
                        answer.setCorrect(isCorrect);
                        int score = isCorrect ? question.getScore() : 0;
                        answer.setScore(score);
                        totalScore += score;
                    } else {
                        answer.setCorrect(false);
                        answer.setScore(0);
                    }
                } catch (NumberFormatException e) {
                    answer.setCorrect(false);
                    answer.setScore(0);
                }
            } else if (question.getQuestionType() == QuestionType.SENTENCE) {
                answer.setCorrect(false);
                answer.setScore(0);
            }
        }

        status.setSubmitted(true);
        status.setSubmittedAt(LocalDateTime.now());
        status.setTotalScore(totalScore);

        return totalScore;
    }

    public int getStudentExamScore(Long courseId, Long examId, Long studentId) {
        verifyService.isEnrolled(courseId, studentId);
        verifyService.isExistExam(courseId, examId);

        StudentExamStatus status = studentExamStatusRepository
                .findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험 응시 정보가 없습니다."));

        if (!status.isSubmitted()) {
            throw new GlobalExceptionHandler.AccessDeniedException("아직 시험을 제출하지 않았습니다.");
        }

        return status.getTotalScore();
    }

    public Map<Long, String> getSavedAnswers(Long courseId, Long examId, Long studentId) {
        verifyService.isEnrolled(courseId, studentId);
        verifyService.isExistExam(courseId, examId);

        StudentExamStatus status = studentExamStatusRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("임시 저장된 시험 정보가 없습니다."));

        List<StudentAnswer> answers = studentAnswerRepository.findByStudentExamStatus(status);
        return answers.stream()
                .collect(Collectors.toMap(
                        a -> a.getQuestion().getId(),
                        StudentAnswer::getAnswer
                ));
    }

    private StudentExamStatus getOrCreateStudentExamStatus(Long studentId, Long examId, boolean createIfNotExist) {
        return studentExamStatusRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseGet(() -> {
                    if (!createIfNotExist) {
                        throw new GlobalExceptionHandler.NotFoundException("임시저장을 위해 시험 상태를 찾을 수 없습니다.");
                    }
                    StudentExamStatus newStatus = new StudentExamStatus();
                    newStatus.setStudent((Student) memberRepository.findById(studentId).get());
                    newStatus.setExam(examRepository.getReferenceById(examId));
                    return studentExamStatusRepository.save(newStatus);
                });
    }

    private StudentAnswer findOrCreateAnswer(StudentExamStatus status, Question question) {
        return studentAnswerRepository.findByStudentExamStatusAndQuestion(status, question)
                .orElseGet(() -> {
                    StudentAnswer answer = new StudentAnswer();
                    answer.setStudentExamStatus(status);
                    answer.setQuestion(question);
                    return studentAnswerRepository.save(answer);
                });
    }

    private void validateExamPeriod(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new GlobalExceptionHandler.SubmitExamDurationException(startTime.toString(), endTime.toString(), now.toString());
        }
    }

}
