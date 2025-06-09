package com.docker.backend.dto.exam;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.Exam;
import com.docker.backend.enums.ExamStatus;

public class ExamMapper {

    public static Exam toEntity(ExamCreateDTO dto, Course course) {
        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setStartTime(dto.getStartTime());
        exam.setEndTime(dto.getEndTime());
        exam.setStatus(ExamStatus.PREPARING);
        return exam;
    }
}
