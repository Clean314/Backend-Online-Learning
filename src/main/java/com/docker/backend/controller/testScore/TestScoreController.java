package com.docker.backend.controller.testScore;

import com.docker.backend.config.AuthUtil;
import com.docker.backend.entity.user.Educator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/learn/test-scores")
public class TestScoreController {

    private final AuthUtil authUtil;

    @GetMapping("/{courseId}")
    @PreAuthorize("hasRole('EDUCATOR')")
    public String getMyTestsByCourse(Authentication authentication, @PathVariable("courseId") String courseId) {
        Educator educator = authUtil.getEducator(authentication);

        return "";
    }

}