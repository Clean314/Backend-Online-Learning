package com.docker.backend.dto.exam;

import com.docker.backend.entity.Exam;
import com.docker.backend.entity.Question;
import com.docker.backend.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    private Long id;
    private String title;
    private String description;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    private ExamStatus status;

    @JsonProperty("course_id")
    private Long courseId;

    private List<Question> questions;

    public static ExamDTO of(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setStatus(exam.getStatus());
        dto.setCourseId(exam.getCourse().getId());
        dto.setQuestions(exam.getQuestions());
        return dto;
    }
}
