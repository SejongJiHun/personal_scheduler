package com.scheduler.personalscheduler.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors()  // 🔴 CORS 설정 활성화
                .and()
                // CSRF 보호 비활성화 (API 서버에선 일반적으로 비활성화함)
                .csrf(csrf -> csrf.disable())

                // 요청별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/html/**", "/css/**", "/js/**", "/favicon.ico").permitAll()
                        .requestMatchers("/api/signup", "/api/login").permitAll()
                        .requestMatchers("/api/**").authenticated() // 나머지 API는 인증 필요
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // ✅ 명시해주면 더 안정적
                );


        // 설정된 시큐리티 필터 체인 빌드 후 반환
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5500")); // 또는 file:// 안 쓰면 프론트 포트 명시
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 🔥 세션 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }



}
