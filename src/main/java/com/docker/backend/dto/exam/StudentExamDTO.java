package com.docker.backend.dto.exam;

import com.docker.backend.dto.exam.question.StudentQuestionDTO;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StudentExamDTO{
    private Long id;
    private Long courseId;

    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ExamStatus status;

    private List<StudentQuestionDTO> questions;
}
