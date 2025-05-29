package com.scheduler.personalscheduler.config;

import com.scheduler.personalscheduler.auth.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/api/me", "/api/schedules/**") // 보호할 경로 지정
                .excludePathPatterns("/api/login", "/api/signup", "/api/logout"); // 인증 필요 없는 경로
    }
}
