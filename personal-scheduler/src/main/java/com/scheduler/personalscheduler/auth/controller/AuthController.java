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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequestDto dto, HttpServletRequest request, HttpServletResponse response) {
        authService.login(dto, request);

        // 테스트용: 세션 ID 로그
        String sessionId = request.getSession().getId();
        log.info("✅ 세션 ID 확인용: {}", sessionId);

        // 강제로 Set-Cookie 삽입 (디버깅용)
        response.addHeader("Set-Cookie", "debug-session=" + sessionId + "; Path=/; HttpOnly");

        return ResponseEntity.ok(ApiResponse.success("로그인 성공"));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공"));
    }

    // 로그인 세션 체크
//    @GetMapping("/me")
//    public ResponseEntity<ApiResponse> getMyInfo(@SessionAttribute(AuthService.LOGIN_USER) Long userId) {
//        User user = userService.findById(userId);
//        return ResponseEntity.ok(ApiResponse.success(new UserResponseDto(user))); // 이메일, 닉네임 반환
//    }


    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getMyInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(AuthService.LOGIN_USER) == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED); // 403 반환
        }

        // ✅ 세션 ID 확인용 로그 추가
        log.info("✅ [/api/me] 세션 ID: {}", session.getId());

        Long userId = (Long) session.getAttribute(AuthService.LOGIN_USER);
        User user = userService.findById(userId);

        return ResponseEntity.ok(ApiResponse.success(new UserResponseDto(user)));
    }


    @GetMapping("/api/test-interceptor")
    public ResponseEntity<String> testInterceptor(HttpServletRequest request) {
        return ResponseEntity.ok("인터셉터 테스트 성공");
    }



}
