package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.dto.exam.question.StudentAnswerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentAnswerMapper {
    @Mapping(target = "questionId", source = "question.id")
    StudentAnswerDTO toDto(StudentAnswer answer);
}