package com.scheduler.personalscheduler.schedule.controller;


import com.scheduler.personalscheduler.schedule.domain.Schedule;
import com.scheduler.personalscheduler.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Schedule create(@RequestBody Schedule schedule){
        return scheduleService.create(schedule);
    }

    // 보관함, 오늘, 내일, 과거 필터
    @GetMapping
    public List<Schedule> getByFilter(@RequestParam(name = "filter", defaultValue = "all") String filter){
        return scheduleService.getByFilter(filter);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        scheduleService.delete(id);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public Schedule update(@PathVariable("id") Long id, @RequestBody Schedule updatedSchedule) {
        return scheduleService.update(id, updatedSchedule);
    }
}
