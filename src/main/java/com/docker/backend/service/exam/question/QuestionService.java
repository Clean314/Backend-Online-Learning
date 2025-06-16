package com.docker.backend.service.exam.question;

import com.docker.backend.dto.exam.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.dto.exam.question.QuestionMapper;
import com.docker.backend.entity.exam.question.Question;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
import com.docker.backend.repository.exam.ExamRepository;
import com.docker.backend.repository.exam.question.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
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

        questionRepository.findByExamIdAndId(examId, questionId)
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));

        Question question = QuestionMapper.toEntity(questionForm);
        question.setExam(examRepository.findById(examId).get());
        question.setId(questionId);
        return EducatorQuestionDTO.of(questionRepository.save(question));
    }

    @Transactional
    public void deleteQuestion(Long educatorId, Long courseId, Long examId, Long questionId) {
        verify(educatorId, courseId, examId);
        questionRepository.deleteByExamIdAndId(examId, questionId);
    }

    private void verify(Long educatorId, Long courseId, Long examId) {
        if (!courseRepository.existsByIdAndEducator_Id(courseId, educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("강의 및 시험 접근 권한이 없습니다.");
        }
        if(!examRepository.existsByCourseIdAndId(courseId, examId)) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
    }

}