package com.docker.backend.dto.exam;

import com.docker.backend.entity.exam.Exam;
import com.docker.backend.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StudentExamDTO {
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

    private List<StudentQuestionDTO> questions;

    public static StudentExamDTO of(Exam exam) {
        StudentExamDTO dto = new StudentExamDTO();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setStatus(exam.getStatus());
        dto.setCourseId(exam.getCourse().getId());

        List<StudentQuestionDTO> questionDTOs =
                exam.getQuestions()
                        .stream()
                        .map(StudentQuestionDTO::of)
                        .collect(Collectors.toList());
        dto.setQuestions(questionDTOs);

        return dto;
    }
}
