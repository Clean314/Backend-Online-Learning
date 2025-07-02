package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    StudentExamSubmissionDTO toDto(StudentExamStatus status, List<StudentAnswer> answers);
}