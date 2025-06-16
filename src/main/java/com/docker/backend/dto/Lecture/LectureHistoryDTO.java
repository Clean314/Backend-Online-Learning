package com.docker.backend.dto.Lecture;

import com.docker.backend.entity.lecture.Lecture;
import com.docker.backend.entity.user.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class LectureHistoryDTO {

    @JsonProperty("lecture_history_id")
    private Long lectureHistoryId;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("lecture_id")
    private Long lectureId;

    @JsonProperty("watch_time")
    private Double watchedTime;
    private Boolean attendance;
}
