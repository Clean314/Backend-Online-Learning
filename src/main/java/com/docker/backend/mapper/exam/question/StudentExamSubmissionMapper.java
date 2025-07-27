package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = StudentAnswerMapper.class)
public interface StudentExamSubmissionMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.name")
    StudentExamSubmissionDTO toDto(StudentExamStatus status);
}