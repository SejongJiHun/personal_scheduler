package com.scheduler.personalscheduler.service;


import com.scheduler.personalscheduler.dto.SignupRequestDto;
import com.scheduler.personalscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();

        // 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

    }

}
