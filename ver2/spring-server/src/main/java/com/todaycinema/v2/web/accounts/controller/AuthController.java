package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.web.accounts.dto.TokenDto;
import com.todaycinema.v2.web.accounts.dto.TokenRequest;
import com.todaycinema.v2.web.accounts.dto.UserRequest;
import com.todaycinema.v2.web.accounts.dto.UserResponse;
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
    public ResponseEntity<UserResponse> signup(@Validated @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.signup(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Validated @RequestBody UserRequest userRequest) {
        log.info(userRequest.toString());
        return ResponseEntity.ok(authService.login(userRequest));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@Validated @RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.reissue(tokenRequest));
    }
}
