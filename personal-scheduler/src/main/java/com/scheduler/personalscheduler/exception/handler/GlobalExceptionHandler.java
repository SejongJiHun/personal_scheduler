package com.scheduler.personalscheduler.exception.handler;

import com.scheduler.personalscheduler.exception.CustomException;
import com.scheduler.personalscheduler.exception.ErrorCode;
import com.scheduler.personalscheduler.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();

        log.warn("[CustomException] {}: {}", code.name(), code.getMessage()); // 메시지만 로깅

        return ResponseEntity.status(code.getStatus())
                .body(new ErrorResponse(code.getMessage()));
    }

    // 일반 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("[Unhandled Exception] {}", e.getMessage()); // 메시지만 로깅 (스택트레이스 없음)

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ErrorResponse("알 수 없는 서버 오류가 발생했습니다."));
    }
}
