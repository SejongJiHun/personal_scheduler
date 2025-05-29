package com.scheduler.personalscheduler.exception;

// 클라이언트에게 줄 응답 형태 통일
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
