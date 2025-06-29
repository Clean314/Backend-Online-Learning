package com.docker.backend.mapper.exam;

import com.docker.backend.domain.exam.Exam;
import com.docker.backend.dto.exam.ExamBaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamBaseMapper {
    @Mapping(source = "course.id", target = "courseId")
    ExamBaseDTO toBaseDto(Exam entity);
}