package com.scheduler.personalscheduler.schedule.dto;

import com.scheduler.personalscheduler.schedule.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isDone;

    // 정적 팩토리 메서드 (엔티티 → DTO)
    public static ScheduleResponseDto from(Schedule schedule) {
        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startDate(schedule.getStartDate())
                .endDate(schedule.getEndDate())
                .isDone(schedule.isDone())
                .build();
    }
}
