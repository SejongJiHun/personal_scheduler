package com.scheduler.personalscheduler.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 해시화(암호화)하기 위한 BCryptPasswordEncoder 빈 등록
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (API 서버에선 일반적으로 비활성화함)
                .csrf(csrf -> csrf.disable())

                // 요청별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // /api/signup은 인증 없이 누구나 접근 가능
                        .requestMatchers("/api/signup").permitAll()
                        // 그 외의 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                );

        // 설정된 시큐리티 필터 체인 빌드 후 반환
        return http.build();
    }


}
