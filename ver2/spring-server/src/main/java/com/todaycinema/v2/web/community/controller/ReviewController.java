package com.todaycinema.v2.web.community.controller;

import com.todaycinema.v2.web.community.dto.MessageResponseDto;
import com.todaycinema.v2.web.community.dto.ReviewRequestDto;
import com.todaycinema.v2.web.community.dto.ReviewResponseDto;
import com.todaycinema.v2.web.community.dto.ReviewsResponseDto;
import com.todaycinema.v2.web.community.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "community")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/community/movie")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 전체 리뷰 조회
     * @param movieId
     * @param authentication
     */
    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewsResponseDto> getReviews(@PathVariable("movieId") long movieId, Authentication authentication) {
        return ResponseEntity.ok(reviewService.getReviews(movieId, authentication));
    }

    /**
     * 리뷰 생성
     * @param movieId
     * @param authentication
     * @param reviewRequestDto
     */
    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable("movieId") long movieId,
            Authentication authentication,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(movieId, authentication, reviewRequestDto));
    }

    /**
     * 단일 리뷰 상세 조회
     * @param movieId
     * @param reviewId
     */
    @GetMapping("/{movieId}/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(movieId, reviewId));
    }

    /**
     * 리뷰 수정
     * @param movieId
     * @param reviewId
     * @param authentication
     * @param reviewRequestDto
     */
    @PutMapping("/{movieId}/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId,
            Authentication authentication,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.updateReview(movieId, reviewId, authentication, reviewRequestDto));
    }

    /**
     * 리뷰 삭제
     * @param movieId
     * @param reviewId
     */
    @DeleteMapping("/{movieId}/review/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(movieId, reviewId));
    }
}
