package com.docker.backend.dto.admin;

import com.docker.backend.entity.course.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminCourseDTO {
    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("course_name")
    private String courseName;

    private String category;

    public AdminCourseDTO(Course course){
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.category = course.getCategory();
    }
}
