package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.config.security.TokenProvider;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.account.Authority;
import com.todaycinema.v2.domain.account.RefreshTokenRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.UserRequest;
import com.todaycinema.v2.web.accounts.dto.UserResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service Test")
class AuthServiceTest {

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("Signup: Success")
    void signup() {
        // given
        UserRequest userRequest = new UserRequest("tester1", "test123");
        given(userRepository.existsByUsername(userRequest.getUsername())).willReturn(false);
        given(userRepository.save(any())).willReturn(new User(1L, "tester1", "asdf", null, Authority.ROLE_USER));

        // when
        UserResponse userResponse = authService.signup(userRequest);

        // then
        Assertions.assertThat(userResponse.getUsername()).isEqualTo("tester1");
    }

    @Test
    @DisplayName("Signup: Fail: Same username")
    void signupFail() {
        // given
        UserRequest userRequest = new UserRequest("tester1", "test123");
        given(userRepository.existsByUsername(userRequest.getUsername())).willReturn(true);

        try {
            // when
            UserResponse userResponse = authService.signup(userRequest);
        } catch (RuntimeException e) {
            // then
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 가입되어 있는 유저입니다");
        }
    }
}