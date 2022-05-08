package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.FollowResponseDto;
import com.todaycinema.v2.web.accounts.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v2/accounts")
@RequiredArgsConstructor
public class SocialController {
    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;
    private final SocialService socialService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<FollowResponseDto> followUser(@PathVariable("userId") long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        FollowResponseDto followResponseDto;
        if (userFollowRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            followResponseDto = socialService.delete(fromUser.getUsername(), toUser.getId());
        } else {
            followResponseDto = socialService.save(fromUser.getUsername(), toUser.getId());
        }
        return ResponseEntity.ok(followResponseDto);
    }
}
