package com.docker.backend.mapper.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.EducatorExamDTO;
import com.docker.backend.dto.exam.ExamCreateDTO;
import com.docker.backend.dto.exam.ExamUpdateDTO;
import com.docker.backend.mapper.exam.question.EducatorQuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducatorExamMapper {

    @Mapping(source = "exam.course.id", target = "courseId")
    EducatorExamDTO toDto(Exam exam);

    List<EducatorExamDTO> toDtoList(List<Exam> byCourseId);

    @Mapping(target = "status", constant = "PREPARING")
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "course", source = "course")
    Exam toEntity(ExamCreateDTO dto, Course course);

    @Mapping(target = "id", source = "examId")
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "course", source = "course")
    Exam toEntity(ExamUpdateDTO dto, Course course, Long examId);

}