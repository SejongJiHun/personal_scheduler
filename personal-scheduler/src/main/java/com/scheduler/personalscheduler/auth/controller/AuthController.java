package com.scheduler.personalscheduler.auth.controller;

import com.scheduler.personalscheduler.auth.dto.LoginRequestDto;
import com.scheduler.personalscheduler.auth.dto.SignupRequestDto;
import com.scheduler.personalscheduler.auth.service.AuthService;
import com.scheduler.personalscheduler.common.dto.ApiResponse;
import com.scheduler.personalscheduler.exception.CustomException;
import com.scheduler.personalscheduler.exception.ErrorCode;
import com.scheduler.personalscheduler.user.domain.User;
import com.scheduler.personalscheduler.user.dto.UserResponseDto;
import com.scheduler.personalscheduler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공"));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequestDto dto, HttpServletRequest request) {
        authService.login(dto, request);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공"));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
    }

    // 로그인 세션 체크
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyInfo(@SessionAttribute(AuthService.LOGIN_USER) Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(ApiResponse.success(new UserResponseDto(user))); // 이메일, 닉네임 반환
    }




}
