package com.docker.backend.mapper.enrollment;

import com.docker.backend.domain.course.Course;
import com.docker.backend.domain.enrollment.Enrollment;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentCourseMapper {

    EnrollmentCourseDTO toDto(Enrollment enrollment);

    List<EnrollmentCourseDTO> toDtoList(List<Enrollment> enrollments);
}