package com.scheduler.personalscheduler.repository;

import com.scheduler.personalscheduler.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {




}
