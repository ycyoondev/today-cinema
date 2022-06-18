package com.todaycinema.v2.web.common;

import com.todaycinema.v2.web.accounts.dto.TokenDto;
import com.todaycinema.v2.web.accounts.dto.UserResponseDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class LoginTester {
    /**
     * 로그인 후 JWT 토큰 정보 반환
     */
    public static String getAuthToken(int port, TestRestTemplate restTemplate) {
        // signup
        String signUpUrl = "http://localhost:" + port + "/v2/accounts/signup";
        String signUpRequestBody = "{\"username\": \"authTester\", \"password\": \"ps123\"}";
        HttpHeaders signUpHeaders = new HttpHeaders();
        signUpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> signUpRequest = new HttpEntity<>(signUpRequestBody, signUpHeaders);
        ResponseEntity<UserResponseDto> signUpResponse = restTemplate.postForEntity(signUpUrl, signUpRequest, UserResponseDto.class);
        // login
        String loginUrl = "http://localhost:" + port + "/v2/accounts/login";
        String loginRequestBody = "{\"username\": \"authTester\", \"password\": \"ps123\"}";
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> loginRequest = new HttpEntity<>(loginRequestBody, loginHeaders);
        ResponseEntity<TokenDto> loginResponse = restTemplate.postForEntity(loginUrl, loginRequest, TokenDto.class);
        String accessToken = loginResponse.getBody().getAccessToken();
        return accessToken;
    }
}
