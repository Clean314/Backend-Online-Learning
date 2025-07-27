package com.docker.backend.mapper.exam;

import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamSubmissionMapper {
    @Mapping(target = "studentId", source = "student.id")
    List<StudentExamSubmissionDTO> toDtoList(List<StudentExamStatus> statuses);
}