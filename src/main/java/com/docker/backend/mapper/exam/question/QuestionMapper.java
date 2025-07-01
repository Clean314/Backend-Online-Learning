package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question toEntity(QuestionForm questionForm);

    EducatorQuestionDTO toEducatorDto(Question q);
    List<EducatorQuestionDTO> toEducatorList(List<Question> byExamId);

    StudentQuestionDTO toStudentDto(Question q);
    List<StudentQuestionDTO> toLStudentList(List<Question> byExamId);
}