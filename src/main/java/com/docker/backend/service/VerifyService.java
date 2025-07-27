package com.docker.backend.service;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enrollment.Enrollment;
import com.docker.backend.domain.enums.Status;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.domain.user.Student;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.StudentAnswerRepository;
import com.docker.backend.repository.exam.StudentExamStatusRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import com.docker.backend.repository.member.MemberRepository;
import com.docker.backend.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final MemberRepository memberRepository;
    private final StudentExamStatusRepository studentExamStatusRepository;
    private final StudentAnswerRepository studentAnswerRepository;

    public Exam verifyEducatorExamOwnership(Long educatorId, Long examId) {
        return examRepository.findByIdAndCourse_Educator_Id(examId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException(
                        "시험 소유자가 아닙니다."
                ));
    }

    public Course isExistCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("강의를 찾을 수 없습니다."));
    }

    public Course isOwnerOfCourse(Long educatorId, Long courseId) {
        return courseRepository.findByIdAndEducator_Id(courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 접근 권한이 없습니다."));
    }

    public Enrollment isEnrolled(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(()-> new GlobalExceptionHandler.AccessDeniedException("강의에 대한 수강 정보가 없습니다."));
    }

    public void isExistEnrollment(Long courseId) {
        if (enrollmentRepository.countByCourseId(courseId) > 0)
            throw new IllegalArgumentException("이미 수강 등록된 강의는 수정 또는 삭제할 수 없습니다.");
    }

    public Exam isExistExam(Long courseId, Long examId) {
        Exam exam = examRepository.findByCourseIdAndId(courseId, examId);
        if (exam == null) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
        return exam;
    }

    public Student isExistStudent(Long studentId) {
        return (Student) memberRepository.findById(studentId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("학생을 찾을 수 없습니다."));
    }

    public Question isExistQuestion(Long examId, Long questionId) {
        return questionRepository.findByExamIdAndId(examId, questionId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException("문제를 찾을 수 없습니다."));
    }

    public StudentExamStatus isExistStudentExamStatus(Long studentId, Long examId){
        return studentExamStatusRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("학생의 시험 상태를 찾을 수 없습니다."));
    }

    public StudentAnswer isExistStudentAnswer(Long statusId, Long questionId){
        return studentAnswerRepository.findByStudentExamStatusIdAndQuestionId(statusId, questionId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 답변이 존재하지 않습니다."));
    }

    // 한 번에 권한과 소속 일관성 확인
    public Exam verifyExamBelongsToCourse(Long educatorId, Long courseId, Long examId) {
        return examRepository.findByIdAndCourseIdAndCourse_Educator_Id(examId, courseId, educatorId)
                .orElseThrow(() -> new GlobalExceptionHandler.AccessDeniedException(
                        "해당 시험이 강좌에 속하지 않거나 권한이 없습니다."
                ));
    }
}