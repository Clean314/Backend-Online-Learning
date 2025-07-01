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

@Mapper(componentModel = "spring", uses = {EducatorQuestionMapper.class})
public interface EducatorExamMapper {

    @Mapping(source = "course.id", target = "courseId")
    EducatorExamDTO toDto(Exam exam);

    List<EducatorExamDTO> toDtoList(List<Exam> byCourseId);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "course", target = "course")
    @Mapping(target = "status", constant = "PREPARING")
    Exam toEntity(ExamCreateDTO dto, Course course);

    @Mapping(target = "id", source = "examId")
    @Mapping(source = "course", target = "course")
    Exam toEntity(ExamUpdateDTO dto, Course course, Long examId);

}
