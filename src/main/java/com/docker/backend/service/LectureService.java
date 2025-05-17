package com.docker.backend.service;

import com.docker.backend.entity.Lecture;
import com.docker.backend.entity.Member;
import com.docker.backend.enums.MemberRole;
import com.docker.backend.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getAllLectures(Member member) {
        if (member.getRole() == MemberRole.EDUCATOR) {
            return lectureRepository.findByTeacherId(member.getId());
        } else if (member.getRole() == MemberRole.STUDENT) {
            return lectureRepository.findByStudentId(member.getId());
        } else {
            throw new IllegalArgumentException("Unknown member role");
        }
    }
}
