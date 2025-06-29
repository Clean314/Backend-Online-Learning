package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.question.Question;
import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
import com.docker.backend.dto.exam.question.QuestionForm;
import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    EducatorQuestionDTO toEducatorDto(Question q);
    StudentQuestionDTO toStudentDto(Question q);
}