package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducatorQuestionMapper {

    EducatorQuestionDTO toDto(Question question);

    List<EducatorQuestionDTO> toDtoList(List<Question> questions);

    @Mapping(target = "id", source = "questionForm.id")
    Question toEntity(QuestionForm questionForm, Exam exam);
}