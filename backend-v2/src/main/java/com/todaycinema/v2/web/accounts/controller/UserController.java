package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.web.accounts.dto.UserResponseDto;
import com.todaycinema.v2.web.accounts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyMemberInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserInfo(username));
    }
}
