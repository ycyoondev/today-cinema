package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.UserFollowing;
import com.todaycinema.v2.domain.repository.UserBlockRepository;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.BlockResponseDto;
import com.todaycinema.v2.web.accounts.dto.FollowResponseDto;
import com.todaycinema.v2.web.accounts.dto.UserProfileDto;
import com.todaycinema.v2.web.accounts.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v2/accounts")
@RequiredArgsConstructor
public class SocialController {
    private final UserFollowRepository userFollowRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserRepository userRepository;
    private final SocialService socialService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<FollowResponseDto> followUser(@PathVariable("userId") long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        FollowResponseDto followResponseDto;
        if (userFollowRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            followResponseDto = socialService.followDelete(fromUser.getUsername(), toUser.getId());
        } else {
            followResponseDto = socialService.followSave(fromUser.getUsername(), toUser.getId());
        }
        return ResponseEntity.ok(followResponseDto);
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<BlockResponseDto> blockUser(@PathVariable("userId") long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        BlockResponseDto blockResponseDto;
        if (userBlockRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            blockResponseDto = socialService.blockDelete(fromUser.getUsername(), toUser.getId());
        } else {
            blockResponseDto = socialService.blockSave(fromUser.getUsername(), toUser.getId());
        }
        return ResponseEntity.ok(blockResponseDto);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable("userId") long userId) {
        UserProfileDto profile = socialService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
}
