package com.docker.backend.mapper.exam;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.exam.Exam;
import com.docker.backend.domain.enums.ExamStatus;
import com.docker.backend.dto.exam.ExamCreateDTO;


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
