package com.todaycinema.v2.web.accounts.service;

import com.todaycinema.v2.domain.*;
import com.todaycinema.v2.domain.repository.MovieWishUserRepository;
import com.todaycinema.v2.domain.repository.UserBlockRepository;
import com.todaycinema.v2.domain.repository.UserFollowRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserBlockRepository userBlockRepository;
    private final MovieWishUserRepository movieWishUserRepository;

    @Transactional
    public FollowResponseDto followUser(long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        FollowResponseDto followResponseDto;
        if (userFollowRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            followResponseDto = followDelete(fromUser.getUsername(), toUser.getId());
        } else {
            followResponseDto = followSave(fromUser.getUsername(), toUser.getId());
        }
        return followResponseDto;
    }

    @Transactional
    public BlockResponseDto blockUser(long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        BlockResponseDto blockResponseDto;
        if (userBlockRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            blockResponseDto = blockDelete(fromUser.getUsername(), toUser.getId());
        } else {
            blockResponseDto = blockSave(fromUser.getUsername(), toUser.getId());
        }
        return blockResponseDto;
    }

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

    public FollowResponseDto followDelete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserFollowing> userFollowingByFromUserAndToUser = userFollowRepository.findUserFollowingByFromUserAndToUser(fromUser.get(), toUser.get());
        userFollowRepository.delete(userFollowingByFromUserAndToUser.get());
        FollowResponseDto followResponseDto = new FollowResponseDto();
        followResponseDto.setMessage("언팔로우에 성공 하였습니다.");
        return followResponseDto;
    }

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

        List<UserBlocked> userBlockeds = userBlockRepository.findUserBlockedsByFromUser(user);
        for (UserBlocked userBlocked : userBlockeds) {
            UserMiniDto userMiniDto = new UserMiniDto(userBlocked.getToUser().getId(), userBlocked.getToUser().getUsername());
            userProfileDto.getBlockings().add(userMiniDto);
        }

        List<MovieWishUser> movieWishUsers = movieWishUserRepository.findMovieWishUsersByUser(user);
        for (MovieWishUser movieWishUser : movieWishUsers) {
            Movie movie = movieWishUser.getMovie();
            MovieDetailMiniDto movieDetailMiniDto = new MovieDetailMiniDto(movie.getId(), movie.getTitle(), movie.getTmdbRating(), movie.getPosterPath());
            userProfileDto.getWishMovies().add(movieDetailMiniDto);
        }

        userProfileDto.setId(userId);
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setIntroduction(user.getIntroduction());

        return userProfileDto;
    }

    @Transactional
    public ProfileUpdateResponseDto updateProfile(long userId, ProfileRequestDto profileRequestDto, Authentication authentication) {
        User user = userRepository.findById(userId).get();
        if (!user.getUsername().equals(authentication.getName())) {
            return new ProfileUpdateResponseDto("계정 주인이 아닙니다.");
        }
        user.setIntroduction(profileRequestDto.getIntroduction());
        return new ProfileUpdateResponseDto("프로필이 업데이트 되었습니다.");
    }
}
