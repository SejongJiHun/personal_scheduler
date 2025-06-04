package com.scheduler.personalscheduler.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ScheduleUpdateDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
