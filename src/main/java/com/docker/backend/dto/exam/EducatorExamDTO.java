package com.docker.backend.dto.exam;

import com.docker.backend.dto.exam.question.EducatorQuestionDTO;
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
public class EducatorExamDTO extends ExamBaseDTO {

    private List<EducatorQuestionDTO> questions;

    private int scoreSum;

    public static EducatorExamDTO of(Exam exam) {
        EducatorExamDTO dto = new EducatorExamDTO();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setStatus(exam.getStatus());
        dto.setCourseId(exam.getCourse().getId());
        return dto;
    }

}
