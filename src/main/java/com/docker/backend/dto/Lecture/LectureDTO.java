package com.docker.backend.dto.Lecture;

import com.docker.backend.entity.course.Course;
import com.docker.backend.entity.lecture.Lecture;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureDTO {

    @JsonProperty("lecture_id")
    private Long lectureId;

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @Size(max = 1000)
    @JsonProperty("video_url")
    private String videoUrl;

    private Double watchedTime = 0.0;

//    private String fileName;
//    private String filePath;
    private String createdAt;
    private String updatedAt;

    @JsonProperty("course_id")
    private Course courseId;

    public LectureDTO(Lecture lecture){
        this.lectureId = lecture.getId();
        this.title = lecture.getTitle();
        this.videoUrl = lecture.getVideoUrl();
        this.createdAt = lecture.getCreatedAt().toString();
        this.updatedAt = lecture.getUpdatedAt().toString();
    }
}
