package com.docker.backend.dto.course;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAttendanceDTO {

    private String studentName;
    private int totalCourse;
    private int attendanceTrue;
    private int attendanceFalse;
    private double attendanceAvg;
}
