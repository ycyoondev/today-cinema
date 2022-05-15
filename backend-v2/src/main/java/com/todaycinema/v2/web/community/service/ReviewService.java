package com.todaycinema.v2.web.community.service;

import com.todaycinema.v2.domain.*;
import com.todaycinema.v2.domain.repository.*;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import com.todaycinema.v2.web.community.dto.ReviewRequestDto;
import com.todaycinema.v2.web.community.dto.ReviewResponseDto;
import com.todaycinema.v2.web.community.dto.ReviewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeUserRepository reviewLikeUserRepository;
    private final ReviewSpoilerCheckUserRepository reviewSpoilerCheckUserRepository;

    public ReviewsResponseDto getReviews(long movieId, Authentication authentication) {
        ReviewsResponseDto reviewsResponseDto = new ReviewsResponseDto();
        Movie movie = movieRepository.findOne(movieId);
        List<Review> reviews = reviewRepository.findAllByMovie(movie);
        for (Review review : reviews) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(
                    review.getId(),
                    new UserMiniDto(review.getUser().getId(), review.getUser().getUsername()),
                    review.getContent(),
                    review.getUserRating(),
                    review.getIsSpoilerSelf(),
                    review.getIsSpoilerChecked(),
                    review.getCreatedAt(),
                    review.getUpdatedAt()
            );
            // repository에서 like user 찾아서 넣기
            List<ReviewLikeUser> reviewLikeUsers = reviewLikeUserRepository.findAllByReview(review);
            List<Long> likeUsers = new ArrayList<>();
            for (ReviewLikeUser reviewLikeUser : reviewLikeUsers) {
                likeUsers.add(reviewLikeUser.getUser().getId());
            }
            reviewResponseDto.setLikeUsers(likeUsers);
            // repository에서 spoiler user 찾아서 넣기
            List<ReviewSpoilerCheckUser> reviewSpoilerCheckUsers = reviewSpoilerCheckUserRepository.findAllByReview(review);
            List<Long> spoilerUsers = new ArrayList<>();
            for (ReviewSpoilerCheckUser reviewSpoilerCheckUser : reviewSpoilerCheckUsers) {
                spoilerUsers.add(reviewSpoilerCheckUser.getUser().getId());
            }
            reviewResponseDto.setSpoilerCheckUsers(spoilerUsers);
            reviewsResponseDto.getReviews().add(reviewResponseDto);
        }
        return reviewsResponseDto;
    }

    @Transactional
    public ReviewResponseDto createReview(long movieId, Authentication authentication, ReviewRequestDto reviewRequestDto) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Movie movie = movieRepository.findOne(movieId);
        Review review = new Review();
        review.setContent(reviewRequestDto.getContent());
        review.setIsSpoilerSelf(reviewRequestDto.isSpoilerSelf());
        review.setIsSpoilerChecked(false);
        review.setUserRating(reviewRequestDto.getUserRating());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        review.setUser(user);
        review.setMovie(movie);
        Review saveReview = reviewRepository.save(review);

        return new ReviewResponseDto(
                saveReview.getId(),
                new UserMiniDto(user.getId(), user.getUsername()),
                saveReview.getContent(),
                saveReview.getUserRating(),
                saveReview.getIsSpoilerSelf(),
                saveReview.getIsSpoilerChecked(),
                saveReview.getCreatedAt(),
                saveReview.getUpdatedAt()
        );
    }


}
