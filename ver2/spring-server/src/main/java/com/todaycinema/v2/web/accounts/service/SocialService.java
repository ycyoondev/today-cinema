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
    public FollowResponse followUser(long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        FollowResponse followResponse;
        if (userFollowRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            followResponse = followDelete(fromUser.getUsername(), toUser.getId());
        } else {
            followResponse = followSave(fromUser.getUsername(), toUser.getId());
        }
        return followResponse;
    }

    @Transactional
    public BlockResponse blockUser(long toUserId, Authentication authentication) {
        User fromUser = userRepository.findByUsername(authentication.getName()).get();
        User toUser = userRepository.findById(toUserId).get();
        BlockResponse blockResponse;
        if (userBlockRepository.existsByFromUserAndToUser(fromUser, toUser)) {
            blockResponse = blockDelete(fromUser.getUsername(), toUser.getId());
        } else {
            blockResponse = blockSave(fromUser.getUsername(), toUser.getId());
        }
        return blockResponse;
    }

    public FollowResponse followSave(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        userFollowRepository.save(UserFollowing.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build());
        FollowResponse followResponse = new FollowResponse();
        followResponse.setMessage("???????????? ?????? ???????????????.");
        return followResponse;
    }

    public FollowResponse followDelete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserFollowing> userFollowingByFromUserAndToUser = userFollowRepository.findUserFollowingByFromUserAndToUser(fromUser.get(), toUser.get());
        userFollowRepository.delete(userFollowingByFromUserAndToUser.get());
        FollowResponse followResponse = new FollowResponse();
        followResponse.setMessage("??????????????? ?????? ???????????????.");
        return followResponse;
    }

    public BlockResponse blockSave(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        userBlockRepository.save(UserBlocked.builder()
                .fromUser(fromUser.get())
                .toUser(toUser.get())
                .build());
        BlockResponse blockResponse = new BlockResponse();
        blockResponse.setMessage("????????? ?????? ???????????????.");
        return blockResponse;
    }

    public BlockResponse blockDelete(String username, Long toUserId) {
        Optional<User> fromUser = userRepository.findByUsername(username);
        Optional<User> toUser = userRepository.findById(toUserId);
        Optional<UserBlocked> userBlockedByFromUserAndToUser = userBlockRepository.findUserBlockedByFromUserAndToUser(fromUser.get(), toUser.get());
        userBlockRepository.delete(userBlockedByFromUserAndToUser.get());
        BlockResponse blockResponse = new BlockResponse();
        blockResponse.setMessage("?????? ????????? ?????? ???????????????.");
        return blockResponse;
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
    public ProfileUpdateResponse updateProfile(long userId, ProfileRequest profileRequest, Authentication authentication) {
        User user = userRepository.findById(userId).get();
        if (!user.getUsername().equals(authentication.getName())) {
            return new ProfileUpdateResponse("?????? ????????? ????????????.");
        }
        user.setIntroduction(profileRequest.getIntroduction());
        return new ProfileUpdateResponse("???????????? ???????????? ???????????????.");
    }
}
