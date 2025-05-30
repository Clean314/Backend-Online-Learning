package com.docker.backend.dto;

import com.docker.backend.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureDTO {

    @JsonProperty("lecture_id")
    private Long LectureId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 1000)
    private String videoUrl;


//    private String fileName;
//    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonProperty("course_id")
    private Course courseId;
}
