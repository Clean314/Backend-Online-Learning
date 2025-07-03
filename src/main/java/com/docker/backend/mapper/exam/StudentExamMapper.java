package com.docker.backend.mapper.exam;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.StudentExamDTO;
import com.docker.backend.mapper.exam.question.StudentQuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentQuestionMapper.class})
public interface StudentExamMapper {
    @Mapping(source = "course.id", target = "courseId")
    StudentExamDTO toDto(Exam exam);

    List<StudentExamDTO> toDtoList(List<Exam> byCourseId);

}