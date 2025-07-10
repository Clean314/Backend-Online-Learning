package com.docker.backend.mapper.enrollment;

import com.docker.backend.domain.enrollment.Enrollment;
import com.docker.backend.dto.enrollment.EnrollmentCourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentCourseMapper {
    @Mapping(source = "course.id", target = "id")
    @Mapping(source = "course.courseName", target = "courseName")
    @Mapping(source = "course.educator.name", target = "educatorName")
    @Mapping(source = "course.category", target = "category")
    @Mapping(source = "course.difficulty", target = "difficulty")
    @Mapping(source = "enrollment.status", target = "status")
    @Mapping(source = "course.point", target = "point")
    @Mapping(source = "course.maxEnrollment", target = "maxEnrollment")
    @Mapping(source = "course.availableEnrollment", target = "availableEnrollment")
    @Mapping(source = "course.description", target = "description")
    EnrollmentCourseDTO toDto(Enrollment enrollment);

    List<EnrollmentCourseDTO> toDtoList(List<Enrollment> enrollments);
}

