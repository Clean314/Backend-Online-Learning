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

    private List<Question> question;

    public static ExamDTO of(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setTitle(exam.getTitle());
        examDTO.setDescription(exam.getDescription());
        examDTO.setStartTime(exam.getStartTime());
        examDTO.setEndTime(exam.getEndTime());
        examDTO.setStatus(exam.getStatus());
        examDTO.setCourseId(exam.getCourse().getId());
        examDTO.setQuestion(exam.getQuestions());
        return examDTO;
    }
}
