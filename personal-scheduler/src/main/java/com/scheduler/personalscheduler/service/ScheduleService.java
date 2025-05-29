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

            // 사이드바의 "오늘"
            // 일정에 오늘이 포함되어 있으면 today
            case "today" -> scheduleRepository.findAll().stream()
                    .filter(s -> !s.getStartDate().isAfter(today) && !s.getEndDate().isBefore(today))
                    .toList();

            // 사이드바의 "미래"
            // 일정에 오늘 이후가 포함되어 있으면 future
            case "future" -> scheduleRepository.findAll().stream()
                    .filter(s -> s.getEndDate().isAfter(today))
                    .toList();

            // 사이드바의 "과거"
            // 일정의 마지막 날이 오늘 이전이면 past
            case "past" -> scheduleRepository.findAll().stream()
                    .filter(s -> s.getEndDate().isBefore(today))
                    .toList();

            default -> scheduleRepository.findAll();
        };
    }


    // 일정 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    // 일정 수정
    public Schedule update(Long id, Schedule updated) {
        Schedule original = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        original.setTitle(updated.getTitle());
        original.setDescription(updated.getDescription());
        original.setStartDate(updated.getStartDate());
        original.setEndDate(updated.getEndDate());

        return scheduleRepository.save(original);
    }


}
