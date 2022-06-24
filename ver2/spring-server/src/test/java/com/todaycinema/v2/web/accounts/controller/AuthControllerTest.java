package com.todaycinema.v2.web.accounts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.todaycinema.v2.web.accounts.dto.TokenDto;
import com.todaycinema.v2.web.accounts.dto.UserResponse;
import com.todaycinema.v2.web.accounts.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Auth Controller Test")
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("Signup: Success")
    @Transactional
    void signup() throws Exception {
        // given
        UserResponse userResponse = new UserResponse("tester1");
        given(authService.signup(any())).willReturn(userResponse);
        String content = "{\"username\": \"tester1\", \"password\": \"tester123\", \"passwordConfirmation\": \"tester123\"}";

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/v2/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tester1"));
    }

    @Test
    @DisplayName("Signup: Fail: Wrong request (blank)")
    @Transactional
    void signupFail() throws Exception {
        // given
        UserResponse userResponse = new UserResponse("tester1");
        given(authService.signup(any())).willReturn(userResponse);
        String content = "{\"username\": \"tester1\", \"password\": \" \"}";

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/v2/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Login: Success")
    @Transactional
    void login() throws Exception {
        // given
        TokenDto tokenDto = new TokenDto();
        given(authService.login(any())).willReturn(tokenDto);
        String content = "{\"username\": \"tester1\", \"password\": \"tester123\"}";

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/v2/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Login: Fail: Wrong request (blank)")
    @Transactional
    void loginFail() throws Exception {
        // given
        TokenDto tokenDto = new TokenDto();
        given(authService.login(any())).willReturn(tokenDto);
        String content = "{\"username\": \"tester1\", \"password\": \" \"}";

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/v2/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        actions.andDo(print())
                .andExpect(status().isBadRequest());
    }
}