package com.scheduler.personalscheduler.service;


import com.scheduler.personalscheduler.domain.Schedule;
import com.scheduler.personalscheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // 모든 일정 조회
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    // 필터를 통해 오늘, 미래, 과거 일정 조회
    public List<Schedule> getByFilter(String filter) {
        LocalDate today = LocalDate.now();

        return switch (filter) {
            case "today" -> scheduleRepository.findAll().stream()
                    .filter(s -> !s.getStartDate().isAfter(today) && !s.getEndDate().isBefore(today))
                    .toList();

            case "past" -> scheduleRepository.findAll().stream()
                    .filter(s -> s.getEndDate().isBefore(today))
                    .toList();

            case "future" -> scheduleRepository.findAll().stream()
                    .filter(s -> s.getStartDate().isAfter(today))
                    .toList();

            default -> scheduleRepository.findAll();  // all or unknown
        };
    }


    // 일정 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
