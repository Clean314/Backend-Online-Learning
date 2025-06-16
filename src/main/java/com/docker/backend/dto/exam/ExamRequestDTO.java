package com.docker.backend.dto.exam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExamRequestDTO {
    @JsonProperty("course_id")
    private Long courseId;

//    @JsonProperty("exam_id")
//    private Long examId;

    @JsonProperty("question_id")
    private Long questionId;
}
