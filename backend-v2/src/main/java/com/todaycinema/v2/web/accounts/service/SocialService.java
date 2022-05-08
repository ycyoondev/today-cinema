package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.UserFollowing;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    @Transactional
    public FollowResponseDto save(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        userFollowRepository.save(UserFollowing.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build());
        FollowResponseDto followResponseDto = new FollowResponseDto();
        followResponseDto.setMessage("팔로우에 성공 하였습니다.");
        return followResponseDto;
    }

    @Transactional
    public FollowResponseDto delete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserFollowing> userFollowingByFromUserAndToUser = userFollowRepository.findUserFollowingByFromUserAndToUser(fromUser.get(), toUser.get());
        userFollowRepository.delete(userFollowingByFromUserAndToUser.get());

        FollowResponseDto followResponseDto = new FollowResponseDto();
        followResponseDto.setMessage("언팔로우에 성공 하였습니다.");
        return followResponseDto;
    }
}
