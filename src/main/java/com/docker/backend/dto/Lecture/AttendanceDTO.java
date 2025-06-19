package com.docker.backend.dto.Lecture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
public class AttendanceDTO {

    @JsonProperty("watch_time")
    private Double watchedTime;
}
