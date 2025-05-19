package com.docker.backend.service.course;

import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseDto createCourse(Educator educator, CourseCreateRequest req) {
        Course course = new Course(req.getName(), req.getCode(), req.getSemester(), req.getMaxEnrollment(), educator);
        return CourseDto.from(courseRepository.save(course));
    }

    @Transactional
    public List<CourseDto> getProfessorCourses(Educator educator) {
        return courseRepository.findByEducatorId(educator)
                .stream()
                .map(CourseDto::from)
                .toList();
    }

}
