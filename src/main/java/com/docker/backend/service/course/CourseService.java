package com.docker.backend.service.course;

import com.docker.backend.dto.CourseCreateRequest;
import com.docker.backend.entity.Course;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.MemberRepository;
import com.docker.backend.repository.course.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    public Course createCourse(Educator educator, CourseCreateRequest req) {
        Course course = new Course();
        course.setCourseName(req.getName());
        course.setMaxEnrollment(req.getMaxEnrollment());
        course.setPoint(req.getPoint());
        course.setEducator(educator);
        return courseRepository.save(course);
    }

    public List<Course> getCoursesByEducator(Educator educator) {
        return courseRepository.findByEducatorId(educator.getId());
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

}
