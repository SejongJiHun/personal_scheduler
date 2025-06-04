package com.scheduler.personalscheduler.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 생성 아이디

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, unique = true)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private String role = "USER"; // 역할

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // 필요한 경우에만 명확한 setter-like method 제공
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}

