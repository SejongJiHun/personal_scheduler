package com.scheduler.personalscheduler.controller;

import com.scheduler.personalscheduler.dto.SignupRequestDto;
import com.scheduler.personalscheduler.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}
