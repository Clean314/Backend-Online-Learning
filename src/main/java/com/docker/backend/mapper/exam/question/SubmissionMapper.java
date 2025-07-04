package com.docker.backend.mapper.exam.question;

import com.docker.backend.domain.exam.StudentAnswer;
import com.docker.backend.domain.exam.StudentExamStatus;
import com.docker.backend.dto.exam.StudentExamSubmissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {

    // 학생 시험 상태와 답안 리스트를 받아 DTO 로 변환
    @Mapping(target = "studentId", source = "status.student.id")
    StudentExamSubmissionDTO toDto(StudentExamStatus status, List<StudentAnswer> answers);

}