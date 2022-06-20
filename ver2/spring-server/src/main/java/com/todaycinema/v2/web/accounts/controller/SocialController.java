package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.web.accounts.dto.*;
import com.todaycinema.v2.web.accounts.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v2/accounts")
@RequiredArgsConstructor
public class SocialController {
    private final SocialService socialService;

    @PostMapping("/follow/{userId}")
    public ResponseEntity<FollowResponseDto> followUser(@PathVariable("userId") long toUserId, Authentication authentication) {
        return ResponseEntity.ok(socialService.followUser(toUserId, authentication));
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<BlockResponseDto> blockUser(@PathVariable("userId") long toUserId, Authentication authentication) {
        return ResponseEntity.ok(socialService.blockUser(toUserId, authentication));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDto> getProfile(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(socialService.getProfile(userId));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ProfileUpdateResponseDto> updateProfile(
            @PathVariable("userId") long userId,
            @RequestBody ProfileRequestDto profileRequestDto,
            Authentication authentication) {
        return ResponseEntity.ok(socialService.updateProfile(userId, profileRequestDto, authentication));
    }
}
