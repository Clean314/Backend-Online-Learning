package com.docker.backend.dto.exam;

import com.docker.backend.entity.Exam;
import com.docker.backend.enums.ExamStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.title = exam.getTitle();
        this.description = exam.getDescription();
        this.startTime = exam.getStartTime();
        this.endTime = exam.getEndTime();
        this.status = exam.getStatus();
        this.courseId = exam.getCourse().getId();
    }
}
