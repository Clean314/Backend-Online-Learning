package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentQuestionMapper {
    StudentQuestionDTO toDto(Question question);
    List<StudentQuestionDTO> toDtoList(List<Question> questions);
}
