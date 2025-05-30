package com.docker.backend.controller.Lecture;

import com.docker.backend.service.LectureService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@PreAuthorize("hasRole('EDUCATOR')")
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/create/url")
    public void createVideo(){

    }
}
