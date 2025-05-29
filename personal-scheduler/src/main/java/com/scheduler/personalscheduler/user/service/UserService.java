package com.scheduler.personalscheduler.user.service;

import com.scheduler.personalscheduler.exception.CustomException;
import com.scheduler.personalscheduler.exception.ErrorCode;
import com.scheduler.personalscheduler.user.domain.User;
import com.scheduler.personalscheduler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
