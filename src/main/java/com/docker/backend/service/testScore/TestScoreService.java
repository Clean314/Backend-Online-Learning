package com.docker.backend.service.testScore;

import com.docker.backend.entity.TestScore;
import com.docker.backend.entity.user.Educator;
import com.docker.backend.repository.testScore.TestScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestScoreService {
    private final TestScoreRepository testScoreRepository;

    public List<TestScore> findByCourseAndEducator(Long courseId, Educator educator) {
        return testScoreRepository.findByCourseId(courseId);
    }


}