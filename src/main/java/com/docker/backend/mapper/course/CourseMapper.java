package com.docker.backend.mapper.course;

import com.docker.backend.domain.course.Course;
import com.docker.backend.dto.course.CourseCreateDTO;
import com.docker.backend.dto.course.CourseDTO;
import com.docker.backend.dto.course.CourseUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "courseId", source = "id")
    CourseDTO toDTO(Course course);

    @Mapping(target = "courseId", source = "id")
    List<CourseDTO> toListDTO(List<Course> courses);

    Course toEntity(Long id, CourseUpdateDTO courseUpdateDTO);

    @Mapping(target = "educator.id", source = "id")
    @Mapping(target = "availableEnrollment", source = "courseCreateDTO.maxEnrollment")
    Course toEntity(Long id, CourseCreateDTO courseCreateDTO);
}