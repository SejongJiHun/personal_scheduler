package com.scheduler.personalscheduler.auth.service;


import com.scheduler.personalscheduler.auth.dto.LoginRequestDto;
import com.scheduler.personalscheduler.exception.CustomException;
import com.scheduler.personalscheduler.exception.ErrorCode;
import com.scheduler.personalscheduler.user.domain.User;
import com.scheduler.personalscheduler.auth.dto.SignupRequestDto;
import com.scheduler.personalscheduler.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public static final String LOGIN_USER = "LOGIN_USER";

    // 회원가입
    public void signup(SignupRequestDto signupRequestDto){

        // 이메일을 소문자로 변환. Locale.ROOT를 지정해주면 언어 중립적이고 국제화 안전성이 보장
        String email = signupRequestDto.getEmail().toLowerCase(Locale.ROOT);

        // 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // User 객체 생성
        User user = new User(email, encodedPassword, signupRequestDto.getNickname());

        // db에 저장
        userRepository.save(user);

    }

    // 로그인
    public void login(LoginRequestDto loginRequestDto, HttpServletRequest request) {

        // 이메일을 소문자로 변환. Locale.ROOT를 지정해주면 언어 중립적이고 국제화 안전성이 보장
        String email = loginRequestDto.getEmail().toLowerCase(Locale.ROOT);

        // 이메일로 User 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 사용자가 입력한 비밀번호와 조회한 User의 비밀번호를 비교
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        request.getSession().setAttribute(LOGIN_USER, user.getId());
    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }


}
