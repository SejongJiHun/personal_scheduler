package com.scheduler.personalscheduler.controller;


import com.scheduler.personalscheduler.domain.Schedule;
import com.scheduler.personalscheduler.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    ScheduleService scheduleService;

    @PostMapping
    public Schedule create(@RequestBody Schedule schedule){
        return scheduleService.create(schedule);
    }

    @GetMapping
    public List<Schedule> getByFilter(@RequestParam(defaultValue = "all") String filter){
        return scheduleService.getByFilter(filter);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        scheduleService.delete(id);
    }

}
