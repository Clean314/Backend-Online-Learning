package com.docker.backend.service.exam.question;

import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.mapper.exam.question.QuestionMapper;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.enrollment.EnrollmentRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ExamRepository examRepository;

    public List<EducatorQuestionDTO> EducatorGetAllQuestionByExamId(Long educatorId, Long courseId, Long examId) {
        verify(educatorId, courseId, examId);
        return questionRepository.findByExamId(examId)
                .stream().map(EducatorQuestionDTO::of).collect(Collectors.toList());
    }

    public EducatorQuestionDTO EducatorCreateQuestion(Long educatorId, Long courseId, Long examId, QuestionForm questionForm) {
        verify(educatorId, courseId, examId);
        Question question = QuestionMapper.toEntity(questionForm);
        question.setExam(examRepository.findById(examId).get());
        return EducatorQuestionDTO.of(questionRepository.save(question));
    }

    public EducatorQuestionDTO EducatorUpdateQuestionById(Long educatorId, Long courseId, Long examId, Long questionId, QuestionForm questionForm) {
        verify(educatorId, courseId, examId);

        Question question = questionRepository.findByExamIdAndId(examId, questionId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));

        question.setNumber(questionForm.getNumber());
        question.setContent(questionForm.getContent());
        question.setAnswer(questionForm.getAnswer());
        question.setScore(questionForm.getScore());
        question.setQuestionType(questionForm.getQuestionType());
        question.setChoices(questionForm.getChoices());

        return EducatorQuestionDTO.of(questionRepository.save(question));
    }


    public void deleteQuestion(Long educatorId, Long courseId, Long examId, Long questionId) {
        verify(educatorId, courseId, examId);
        Question question = questionRepository.findByExamIdAndId(examId, questionId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));
        questionRepository.delete(question);
    }


    public List<StudentQuestionDTO> StudentGetAllQuestionsByExamId(Long studentId, Long courseId, Long examId) {
        studentVerify(studentId, courseId, examId);
        return questionRepository.findByExamId(examId)
                .stream().map(StudentQuestionDTO::of).collect(Collectors.toList());
    }

    private void verify(Long educatorId, Long courseId, Long examId) {
        if (!courseRepository.existsByIdAndEducator_Id(courseId, educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("강의 및 시험 접근 권한이 없습니다.");
        }
        if(!examRepository.existsByCourseIdAndId(courseId, examId)) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
    }

    private void studentVerify(Long studentId, Long courseId, Long examId){
        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("해당 강의에 대한 수강 등록이 없습니다."));

        if(!examRepository.existsByCourseIdAndId(courseId, examId)) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
    }

}