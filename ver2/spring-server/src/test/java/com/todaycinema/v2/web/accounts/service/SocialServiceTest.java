package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.config.security.TokenProvider;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.account.Authority;
import com.todaycinema.v2.domain.repository.MovieWishUserRepository;
import com.todaycinema.v2.domain.repository.UserBlockRepository;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.BlockResponse;
import com.todaycinema.v2.web.accounts.dto.FollowResponse;
import com.todaycinema.v2.web.accounts.dto.ProfileRequest;
import com.todaycinema.v2.web.accounts.dto.ProfileUpdateResponse;
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
        FollowResponse followResponse = new FollowResponse("팔로우에 성공 하였습니다.");

        Optional<User> fromUser = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());
        Optional<User> toUser = Optional.of(User.builder().id(2L).username("test2").authority(Authority.ROLE_USER).build());

        given(userRepository.findByUsername(any())).willReturn(fromUser);
        given(userRepository.findById(anyLong())).willReturn(toUser);
        given(userFollowRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        FollowResponse followResponse1 = socialService.followUser(1L, authentication);

        // then
        Assertions.assertThat(followResponse1.getMessage()).isEqualTo(followResponse.getMessage());
    }

    @Test
    @WithMockUser(username = "test1")
    @DisplayName("blockUser: Success: Block")
    @Transactional
    void blockUser() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BlockResponse blockResponse = new BlockResponse("차단에 성공 하였습니다.");

        Optional<User> fromUser = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());
        Optional<User> toUser = Optional.of(User.builder().id(2L).username("test2").authority(Authority.ROLE_USER).build());

        given(userRepository.findByUsername(any())).willReturn(fromUser);
        given(userRepository.findById(anyLong())).willReturn(toUser);
        given(userBlockRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        BlockResponse blockResponse1 = socialService.blockUser(1L, authentication);

        // then
        Assertions.assertThat(blockResponse1.getMessage()).isEqualTo(blockResponse.getMessage());
    }

    @Test
    @WithMockUser(username = "test1")
    @DisplayName("updateProfile: Success")
    @Transactional
    void updateProfile() {
        // given
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ProfileRequest profileRequest = new ProfileRequest("introduction me");
        Optional<User> user = Optional.of(User.builder().id(1L).username("test1").authority(Authority.ROLE_USER).build());

        given(userRepository.findById(anyLong())).willReturn(user);
        given(userBlockRepository.existsByFromUserAndToUser(any(), any())).willReturn(false);

        // when
        ProfileUpdateResponse profileUpdateResponse = socialService.updateProfile(1L, profileRequest,authentication);

        // then
        Assertions.assertThat(profileUpdateResponse.getMessage()).isEqualTo("프로필이 업데이트 되었습니다.");
    }
}