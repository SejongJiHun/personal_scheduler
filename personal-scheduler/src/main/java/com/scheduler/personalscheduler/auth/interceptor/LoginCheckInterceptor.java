package com.scheduler.personalscheduler.auth.interceptor;

import com.scheduler.personalscheduler.auth.service.AuthService;
import com.scheduler.personalscheduler.exception.CustomException;
import com.scheduler.personalscheduler.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object loginUser = request.getSession().getAttribute(AuthService.LOGIN_USER);
        if (loginUser == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED); // 예외는 GlobalExceptionHandler에서 처리됨
        }
        return true;
    }
}
