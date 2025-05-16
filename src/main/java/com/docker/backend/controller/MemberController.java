package com.docker.backend.controller;

import com.docker.backend.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {


    }

    @GetMapping("/my-page")
    public String myPage() {
        return "my-page";
    }
}