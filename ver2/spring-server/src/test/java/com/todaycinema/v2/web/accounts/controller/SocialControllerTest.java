package com.todaycinema.v2.web.accounts.controller;

import com.todaycinema.v2.config.security.TokenProvider;
import com.todaycinema.v2.web.accounts.dto.*;
import com.todaycinema.v2.web.accounts.service.SocialService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Social Controller Test")
class SocialControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SocialService socialService;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("followUser: Success")
    @WithMockUser(username = "auth123")
    void followUserFollow() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        FollowResponseDto followResponseDto = new FollowResponseDto("팔로우에 성공 하였습니다.");
        given(socialService.followUser(anyLong(), any())).willReturn(followResponseDto);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .post("/v2/accounts/follow/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON).content(""));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("팔로우에 성공 하였습니다."));
    }

    @Test
    @DisplayName("blockUser: Success")
    @WithMockUser(username = "auth123")
    void blockUser() throws Exception {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        BlockResponseDto blockResponseDto = new BlockResponseDto("차단에 성공 하였습니다.");
        given(socialService.blockUser(anyLong(), any())).willReturn(blockResponseDto);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .post("/v2/accounts/block/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON).content(""));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("차단에 성공 하였습니다."));
    }

    @Test
    @DisplayName("getProfile: Success")
    void getProfile() throws Exception {
        // given
        UserProfileDto userProfileDto = UserProfileDto.builder().id(1L).username("test1").build();
        given(socialService.getProfile(anyLong())).willReturn(userProfileDto);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .get("/v2/accounts/profile/1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("updateProfile: Success")
    @WithMockUser(username = "auth123")
    void updateProfile() throws Exception {
        // given
        ProfileUpdateResponseDto profileUpdateResponseDto = new ProfileUpdateResponseDto("프로필이 업데이트 되었습니다.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        given(socialService.updateProfile(anyLong(), any(), any())).willReturn(profileUpdateResponseDto);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .put("/v2/accounts/profile/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON).content("{\"introduction\": \"tester1\"}"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로필이 업데이트 되었습니다."));
    }
}