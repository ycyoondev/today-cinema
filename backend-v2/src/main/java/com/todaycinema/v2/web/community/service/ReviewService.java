package com.todaycinema.v2.web.community.service;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.Review;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.domain.repository.ReviewRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
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

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public ReviewsResponseDto getReviews(long movieId, Authentication authentication) {
        return null;
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
