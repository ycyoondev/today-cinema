package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.config.security.TokenProvider;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.account.Authority;
import com.todaycinema.v2.domain.repository.MovieWishUserRepository;
import com.todaycinema.v2.domain.repository.UserBlockRepository;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.BlockResponseDto;
import com.todaycinema.v2.web.accounts.dto.FollowResponseDto;
import com.todaycinema.v2.web.accounts.dto.ProfileRequestDto;
import com.todaycinema.v2.web.accounts.dto.ProfileUpdateResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Social Service Test")
class SocialServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFollowRepository userFollowRepository;
    @Mock
    private UserBlockRepository userBlockRepository;
    @Mock
    private MovieWishUserRepository movieWishUserRepository;
    @InjectMocks
    private SocialService socialService;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @WithMockUser(username = "test1")
    @DisplayName("followUser: Success: Follow")
    @Transactional
    void followUser() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FollowResponseDto followResponseDto = new FollowResponseDto("팔로우에 성공 하였습니다.");

        Optional<User> fromUser = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());
        Optional<User> toUser = Optional.of(User.builder().id(2L).username("test2").authority(Authority.ROLE_USER).build());

        given(userRepository.findByUsername(any())).willReturn(fromUser);
        given(userRepository.findById(anyLong())).willReturn(toUser);
        given(userFollowRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        FollowResponseDto followResponseDto1 = socialService.followUser(1L, authentication);

        // then
        Assertions.assertThat(followResponseDto1.getMessage()).isEqualTo(followResponseDto.getMessage());
    }

    @Test
    @WithMockUser(username = "test1")
    @DisplayName("blockUser: Success: Block")
    @Transactional
    void blockUser() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BlockResponseDto blockResponseDto = new BlockResponseDto("차단에 성공 하였습니다.");

        Optional<User> fromUser = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());
        Optional<User> toUser = Optional.of(User.builder().id(2L).username("test2").authority(Authority.ROLE_USER).build());

        given(userRepository.findByUsername(any())).willReturn(fromUser);
        given(userRepository.findById(anyLong())).willReturn(toUser);
        given(userBlockRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        BlockResponseDto blockResponseDto1 = socialService.blockUser(1L, authentication);

        // then
        Assertions.assertThat(blockResponseDto1.getMessage()).isEqualTo(blockResponseDto.getMessage());
    }

    @Test
    @WithMockUser(username = "test1")
    @DisplayName("updateProfile: Success")
    @Transactional
    void updateProfile() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProfileRequestDto profileRequestDto = new ProfileRequestDto("introduction me");
        Optional<User> user = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());

        given(userRepository.findById(anyLong())).willReturn(user);
        given(userBlockRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        ProfileUpdateResponseDto profileUpdateResponseDto = socialService.updateProfile(1L, profileRequestDto,authentication);

        // then
        Assertions.assertThat(profileUpdateResponseDto.getMessage()).isEqualTo("프로필이 업데이트 되었습니다.");
    }
}