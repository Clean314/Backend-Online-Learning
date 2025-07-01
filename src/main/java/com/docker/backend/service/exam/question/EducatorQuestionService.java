package com.docker.backend.service.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.exception.GlobalExceptionHandler;
import com.docker.backend.mapper.exam.question.EducatorQuestionMapper;
import com.docker.backend.mapper.exam.question.QuestionMapper;
import com.docker.backend.repository.exam.question.QuestionRepository;
import com.docker.backend.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducatorQuestionService {

    private final QuestionRepository questionRepository;
    private final EducatorQuestionMapper educatorQuestionMapper;
    private final VerifyService verifyService;

    public List<EducatorQuestionDTO> getAllQuestionByExamId(Long educatorId, Long courseId, Long examId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        return educatorQuestionMapper.toDtoList(questionRepository.findByExamId(examId));
    }

    public EducatorQuestionDTO createQuestion(Long educatorId, Long courseId,
                                              Long examId, QuestionForm questionForm) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        Question question = educatorQuestionMapper.toEntity(questionForm);
        question.setExam(verifyService.isExistExam(courseId, examId));

        return educatorQuestionMapper.toDto(questionRepository.save(question));
    }

    public EducatorQuestionDTO updateQuestionById(Long educatorId, Long courseId, Long examId,
                                                  Long questionId, QuestionForm questionForm) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        Question question = verifyService.isExistQuestion(courseId, questionId);
        return educatorQuestionMapper.toDto(
                questionRepository.save(educatorQuestionMapper.toEntity(questionForm)));
    }

    public void deleteQuestion(Long educatorId, Long courseId, Long examId, Long questionId) {
        verifyService.isOwnerOfCourse(educatorId, courseId);
        Question question = verifyService.isExistQuestion(examId, questionId);
        questionRepository.delete(question);
    }

}
