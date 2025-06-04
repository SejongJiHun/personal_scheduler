package com.scheduler.personalscheduler.schedule.service;


import com.scheduler.personalscheduler.schedule.domain.Schedule;
import com.scheduler.personalscheduler.schedule.dto.ScheduleCreateDto;
import com.scheduler.personalscheduler.schedule.dto.ScheduleResponseDto;
import com.scheduler.personalscheduler.schedule.dto.ScheduleUpdateDto;
import com.scheduler.personalscheduler.schedule.repository.ScheduleRepository;
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

    // 일정 생성 (id, isDone, role 조작 방어 위해 파라미터를 바로 save하지 않음)
    public ScheduleResponseDto create(ScheduleCreateDto scheduleCreateDto) {
        Schedule newSchedule = Schedule.create(
                scheduleCreateDto.getTitle(),
                scheduleCreateDto.getDescription(),
                scheduleCreateDto.getStartDate(),
                scheduleCreateDto.getEndDate()
        );
        return ScheduleResponseDto.from(scheduleRepository.save(newSchedule));
    }

    // 모든 일정 조회
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    // 필터를 통해 오늘, 미래, 과거 일정 조회
    public List<ScheduleResponseDto> getByFilter(String filter) {
        LocalDate today = LocalDate.now();

        List<Schedule> schedules = switch (filter) {
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

            // 기본: 전체 일정 반환 (보관함)
            default -> scheduleRepository.findAll();
        };

        // Schedule → ScheduleResponseDto 변환
        return schedules.stream()
                .map(ScheduleResponseDto::from)
                .toList();
    }

    // 일정 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    // 일정 수정 (수정됨: setter 제거 → update 메서드 사용)
    public ScheduleResponseDto update(Long id, ScheduleUpdateDto scheduleUpdateDto) {
        Schedule original = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        original.update(
                scheduleUpdateDto.getTitle(),
                scheduleUpdateDto.getDescription(),
                scheduleUpdateDto.getStartDate(),
                scheduleUpdateDto.getEndDate()
        );

        return ScheduleResponseDto.from(scheduleRepository.save(original));
    }
}
