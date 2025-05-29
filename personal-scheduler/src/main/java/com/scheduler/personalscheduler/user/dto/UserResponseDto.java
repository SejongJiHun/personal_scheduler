package com.scheduler.personalscheduler.user.dto;


import com.scheduler.personalscheduler.user.domain.User;
import lombok.Getter;


@Getter
public class UserResponseDto {
    private final String email;
    private final String nickname;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
