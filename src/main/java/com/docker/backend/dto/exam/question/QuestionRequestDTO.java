package com.docker.backend.dto.exam.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class QuestionRequestDTO {
    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("exam_id")
    private Long examId;

    @JsonProperty("question_id")
    private Long questionId;
}
