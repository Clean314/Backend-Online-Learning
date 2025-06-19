package com.docker.backend.service.exam;

import com.docker.backend.dto.exam.*;
import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.exam.Exam;
import com.docker.backend.entity.exam.StudentAnswer;
import com.docker.backend.entity.exam.StudentExamStatus;
import com.docker.backend.entity.exam.question.Question;
import com.docker.backend.entity.user.Student;
import com.docker.backend.enums.ExamStatus;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.StudentAnswerRepository;
import com.docker.backend.repository.exam.StudentExamStatusRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import com.docker.backend.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.docker.backend.enums.QuestionType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final QuestionRepository questionRepository;
    private final StudentExamStatusRepository studentExamStatusRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final MemberRepository memberRepository;

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
        return examRepository.findByCourseId(courseId)
                .stream().map(StudentExamDTO::of).toList();
    }

    public StudentExamDTO getStudentExamByIdAndCourse(Long courseId, Long studentId, Long examId) {
        verifyEnrollCourse(courseId, studentId);
        Exam exam = isExistExam(courseId, examId);
        Student student = isExistStudent(studentId);

        studentExamStatusRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseGet(() -> {
                    StudentExamStatus status = new StudentExamStatus();
                    status.setStudent(student);
                    status.setExam(exam);
                    status.setSubmitted(false);
                    status.setTotalScore(0);
                    return studentExamStatusRepository.save(status);
                });

        return StudentExamDTO.of(exam);
    }

    public void initializeExamStatus(Long courseId, Long examId, Long studentId) {
        verifyEnrollCourse(courseId, studentId);
        Exam exam = isExistExam(courseId, examId);
        Student student = isExistStudent(studentId);

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
        validateExamPeriod(courseId, examId);
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
        validateExamPeriod(courseId, examId);
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
        verifyEnrollCourse(courseId, studentId);
        isExistExam(courseId, examId);

        StudentExamStatus status = studentExamStatusRepository
                .findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("시험 응시 정보가 없습니다."));

        if (!status.isSubmitted()) {
            throw new GlobalExceptionHandler.AccessDeniedException("아직 시험을 제출하지 않았습니다.");
        }

        return status.getTotalScore();
    }

    private void verifyEnrollCourse(Long courseId, Long studentId) {
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 강의에 대한 수강 등록이 없습니다."));
    }

    private Exam isExistExam(Long courseId, Long examId) {
        Exam exam = examRepository.findByCourseIdAndId(courseId, examId);
        if (exam == null) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
        return exam;
    }

    private Student isExistStudent(Long studentId) {
        return (Student) memberRepository.findById(studentId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("학생을 찾을 수 없습니다."));
    }

    private void validateExamPeriod(Long courseId, Long examId) {
        Exam exam = isExistExam(courseId, examId);
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(exam.getStartTime()) || now.isAfter(exam.getEndTime())) {
            throw new GlobalExceptionHandler.AccessDeniedException("시험 응시 가능 시간이 아닙니다.");
        }
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

    private Course verifyCourseOwnership(Long courseId, Long educatorId) {
        return courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));
    }

}

