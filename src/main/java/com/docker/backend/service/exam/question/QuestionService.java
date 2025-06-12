package com.docker.backend.service.exam.question;

import com.docker.backend.dto.exam.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionCreateUpdateForm;
import com.docker.backend.dto.exam.question.QuestionMapper;
import com.docker.backend.dto.exam.question.QuestionRequestDTO;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.repository.course.CourseRepository;
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
    private final ExamRepository examRepository;

    public List<EducatorQuestionDTO> EducatorGetAllQuestionByExamId(Long educatorId, QuestionRequestDTO questionRequestDTO) {
        verify(educatorId, questionRequestDTO.getCourseId(), questionRequestDTO.getExamId());
        return questionRepository.findByExamId(questionRequestDTO.getExamId())
                .stream().map(EducatorQuestionDTO::of).collect(Collectors.toList());
    }

    public EducatorQuestionDTO EducatorCreateQuestion(Long educatorId, QuestionCreateUpdateForm questionCreateForm) {
        verify(educatorId, questionCreateForm.getCourseId(), questionCreateForm.getExamId());
        return EducatorQuestionDTO.of(questionRepository.save(QuestionMapper.toEntity(questionCreateForm)));
    }

    public EducatorQuestionDTO EducatorUpdateQuestionById(Long educatorId, QuestionCreateUpdateForm questionCreateForm) {
        verify(educatorId, questionCreateForm.getCourseId(), questionCreateForm.getExamId());

        questionRepository.findById(questionCreateForm.getQuestionId())
                .orElseThrow(() -> new GlobalExceptionHandler.NotFoundException("문제를 찾을 수 없습니다."));

        return EducatorQuestionDTO.of(questionRepository.save(QuestionMapper.toEntity(questionCreateForm)));
    }


    private void verify(Long educatorId, Long courseId, Long examId) {
        if (!courseRepository.existsByIdAndEducator_Id(courseId, educatorId)) {
            throw new GlobalExceptionHandler.AccessDeniedException("강의 접근 권한이 없습니다.");
        }
        if(!examRepository.existsById(examId)) {
            throw new GlobalExceptionHandler.NotFoundException("시험을 찾을 수 없습니다.");
        }
    }

}