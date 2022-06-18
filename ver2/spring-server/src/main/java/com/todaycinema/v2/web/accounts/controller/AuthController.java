package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.web.accounts.dto.TokenDto;
import com.todaycinema.v2.web.accounts.dto.TokenRequestDto;
import com.todaycinema.v2.web.accounts.dto.UserRequestDto;
import com.todaycinema.v2.web.accounts.dto.UserResponseDto;
import com.todaycinema.v2.web.accounts.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v2/accounts")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Validated @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Validated @RequestBody UserRequestDto userRequestDto) {
        log.info(userRequestDto.toString());
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@Validated @RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
