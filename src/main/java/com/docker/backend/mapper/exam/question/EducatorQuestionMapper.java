package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducatorQuestionMapper {

    EducatorQuestionDTO toDto(Question question);

    List<EducatorQuestionDTO> toDtoList(List<Question> questions);
}