package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.config.security.SecurityUtil;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUserInfo(String username) {
        return userRepository.findByUsername(username)
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    public UserResponseDto getMyInfo() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

    }
}
