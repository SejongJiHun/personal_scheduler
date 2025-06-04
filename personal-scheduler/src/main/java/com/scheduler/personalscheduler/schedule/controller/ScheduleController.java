package com.scheduler.personalscheduler.schedule.controller;


import com.scheduler.personalscheduler.common.dto.ApiResponse;
import com.scheduler.personalscheduler.schedule.domain.Schedule;
import com.scheduler.personalscheduler.schedule.dto.ScheduleCreateDto;
import com.scheduler.personalscheduler.schedule.dto.ScheduleResponseDto;
import com.scheduler.personalscheduler.schedule.dto.ScheduleUpdateDto;
import com.scheduler.personalscheduler.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody ScheduleCreateDto scheduleCreateDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.create(scheduleCreateDto);
        return ResponseEntity.ok(ApiResponse.success(scheduleResponseDto));
    }

    // 보관함, 오늘, 내일, 과거 필터
    @GetMapping
    public ResponseEntity<ApiResponse> getByFilter(@RequestParam(name = "filter", defaultValue = "all") String filter){
        List<ScheduleResponseDto> result = scheduleService.getByFilter((filter));
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody ScheduleUpdateDto scheduleUpdateDto) {
        return ResponseEntity.ok(ApiResponse.success(scheduleService.update(id, scheduleUpdateDto)));
    }
}
