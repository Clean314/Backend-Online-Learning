package com.docker.backend.mapper.exam;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.mapper.exam.question.StudentQuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExamBaseMapper.class, StudentQuestionMapper.class})
public interface StudentExamMapper {
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "questions", target = "questions")
    StudentExamDTO toDto(Exam exam);
}