package com.scheduler.personalscheduler.service;


import com.scheduler.personalscheduler.domain.User;
import com.scheduler.personalscheduler.dto.SignupRequestDto;
import com.scheduler.personalscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public void signup(SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();

        // 1. 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        // 3. User 객체 생성
        User user = new User(email, encodedPassword, signupRequestDto.getNickname());

        // 4. db에 저장
        userRepository.save(user);

    }

}
