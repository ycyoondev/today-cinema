package com.todaycinema.v2.web.community.service;

import com.todaycinema.v2.domain.*;
import com.todaycinema.v2.domain.repository.*;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import com.todaycinema.v2.web.community.dto.MessageResponse;
import com.todaycinema.v2.web.community.dto.ReviewRequest;
import com.todaycinema.v2.web.community.dto.ReviewResponse;
import com.todaycinema.v2.web.community.dto.ReviewsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ReviewsResponse getReviews(long movieId, Authentication authentication) {
        ReviewsResponse reviewsResponse = new ReviewsResponse();
        Movie movie = movieRepository.findOne(movieId);
        List<Review> reviews = reviewRepository.findAllByMovie(movie);
        for (Review review : reviews) {
            ReviewResponse reviewResponse = new ReviewResponse(
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
            reviewResponse.setLikeUsers(likeUsers);
            // repository에서 spoiler user 찾아서 넣기
            List<ReviewSpoilerCheckUser> reviewSpoilerCheckUsers = reviewSpoilerCheckUserRepository.findAllByReview(review);
            List<Long> spoilerUsers = new ArrayList<>();
            for (ReviewSpoilerCheckUser reviewSpoilerCheckUser : reviewSpoilerCheckUsers) {
                spoilerUsers.add(reviewSpoilerCheckUser.getUser().getId());
            }
            reviewResponse.setSpoilerCheckUsers(spoilerUsers);
            reviewsResponse.getReviews().add(reviewResponse);
        }
        return reviewsResponse;
    }

    @Transactional
    public ReviewResponse createReview(long movieId, Authentication authentication, ReviewRequest reviewRequest) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Movie movie = movieRepository.findOne(movieId);
        Review review = new Review();
        review.setContent(reviewRequest.getContent());
        review.setIsSpoilerSelf(reviewRequest.isSpoilerSelf());
        review.setIsSpoilerChecked(false);
        review.setUserRating(reviewRequest.getUserRating());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());
        review.setUser(user);
        review.setMovie(movie);
        Review saveReview = reviewRepository.save(review);

        return new ReviewResponse(
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


    public ReviewResponse getReview(long movieId, long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.get();
        User user = review.getUser();
        return new ReviewResponse(
                review.getId(),
                new UserMiniDto(user.getId(), user.getUsername()),
                review.getContent(),
                review.getUserRating(),
                review.getIsSpoilerSelf(),
                review.getIsSpoilerChecked(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    @Transactional
    public ReviewResponse updateReview(long movieId, long reviewId, Authentication authentication, ReviewRequest reviewRequest) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.get();
        // 수정
        review.setContent(reviewRequest.getContent());
        review.setIsSpoilerSelf(reviewRequest.isSpoilerSelf());
        review.setUserRating(reviewRequest.getUserRating());
        // 조회
        User user = review.getUser();
        return new ReviewResponse(
                review.getId(),
                new UserMiniDto(user.getId(), user.getUsername()),
                review.getContent(),
                review.getUserRating(),
                review.getIsSpoilerSelf(),
                review.getIsSpoilerChecked(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }

    @Transactional
    public MessageResponse deleteReview(long movieId, long reviewId) {
        reviewRepository.deleteById(reviewId);
        return new MessageResponse("review delete");
    }
}
