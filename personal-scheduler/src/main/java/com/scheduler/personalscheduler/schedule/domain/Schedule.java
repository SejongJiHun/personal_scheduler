package com.scheduler.personalscheduler.schedule.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 생성자 접근 제어자 변경 (수정됨)
@Builder(access = AccessLevel.PRIVATE) // 빌더 접근 제어자 변경 (수정됨)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;  // 일정 제목

    @Column(length = 1500)
    private String description;  // 일정 상세 설명

    @Column(nullable = false)
    private LocalDate startDate;  // 시작일

    @Column(nullable = false)
    private LocalDate endDate;  // 마감일

    @Column(nullable = false)
    private boolean isDone;  // 완료 여부

    // 일정 생성 메서드
    public static Schedule create(String title, String description, LocalDate startDate, LocalDate endDate) {
        return Schedule.builder()
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .isDone(false)
                .build();
    }

    // 일정 수정 메서드
    public void update(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

