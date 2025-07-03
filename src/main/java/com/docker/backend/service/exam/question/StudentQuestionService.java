package com.docker.backend.service.exam.question;

import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import com.docker.backend.mapper.exam.question.StudentQuestionMapper;
import com.docker.backend.repository.exam.question.QuestionRepository;
import com.docker.backend.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentQuestionService {

    private final VerifyService verifyService;
    private final QuestionRepository questionRepository;
    private final StudentQuestionMapper studentQuestionMapper;

    public List<StudentQuestionDTO> getAllQuestionsByExamId(Long studentId, Long courseId, Long examId) {
        verifyService.isEnrolled(studentId, courseId);
        return studentQuestionMapper.toDtoList(questionRepository.findByExamId(examId));
    }


}
