package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.UserBlocked;
import com.todaycinema.v2.domain.UserFollowing;
import com.todaycinema.v2.domain.repository.UserBlockRepository;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.BlockResponseDto;
import com.todaycinema.v2.web.accounts.dto.FollowResponseDto;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import com.todaycinema.v2.web.accounts.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserBlockRepository userBlockRepository;

    @Transactional
    public FollowResponseDto followSave(String username, Long toUserId) {
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
    public FollowResponseDto followDelete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserFollowing> userFollowingByFromUserAndToUser = userFollowRepository.findUserFollowingByFromUserAndToUser(fromUser.get(), toUser.get());
        userFollowRepository.delete(userFollowingByFromUserAndToUser.get());

        FollowResponseDto followResponseDto = new FollowResponseDto();
        followResponseDto.setMessage("언팔로우에 성공 하였습니다.");
        return followResponseDto;
    }

    @Transactional
    public BlockResponseDto blockSave(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        userBlockRepository.save(UserBlocked.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build());
        BlockResponseDto blockResponseDto = new BlockResponseDto();
        blockResponseDto.setMessage("차단에 성공 하였습니다.");
        return blockResponseDto;
    }

    @Transactional
    public BlockResponseDto blockDelete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserBlocked> userBlockedByFromUserAndToUser = userBlockRepository.findUserBlockedByFromUserAndToUser(fromUser.get(), toUser.get());
        userBlockRepository.delete(userBlockedByFromUserAndToUser.get());

        BlockResponseDto blockResponseDto = new BlockResponseDto();
        blockResponseDto.setMessage("차단 해제에 성공 하였습니다.");
        return blockResponseDto;
    }

    public UserProfileDto getProfile(long userId) {
        UserProfileDto userProfileDto = new UserProfileDto();
        User user = userRepository.findById(userId).get();
        List<UserFollowing> userfollowers = userFollowRepository.findUserFollowingsByToUser(user);
        for (UserFollowing userfollower : userfollowers) {
            UserMiniDto userMiniDto = new UserMiniDto(userfollower.getFromUser().getId(), userfollower.getFromUser().getUsername());
            userProfileDto.getFollowers().add(userMiniDto);
        }

        List<UserFollowing> userfollowings = userFollowRepository.findUserFollowingsByFromUser(user);
        for (UserFollowing userfollowing : userfollowings) {
            UserMiniDto userMiniDto = new UserMiniDto(userfollowing.getToUser().getId(), userfollowing.getToUser().getUsername());
            userProfileDto.getFollowings().add(userMiniDto);
        }

        userProfileDto.setId(userId);
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setIntroduction(user.getIntroduction());

        return userProfileDto;
    }
}