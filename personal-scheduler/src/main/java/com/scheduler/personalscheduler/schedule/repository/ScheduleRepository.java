package com.scheduler.personalscheduler.schedule.repository;

import com.scheduler.personalscheduler.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {




}
