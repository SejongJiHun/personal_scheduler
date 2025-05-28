package com.scheduler.personalscheduler.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;  // 일정 제목

    @Column(length = 1500)
    private String description;  // 일정 상세 설명 (선택)

    @Column(nullable = false)
    private LocalDate startDate;  // 시작일

    @Column(nullable = false)
    private LocalDate endDate;  // 마감일

    @Column(nullable = false)
    private boolean isDone;  // 완료 여부
}
