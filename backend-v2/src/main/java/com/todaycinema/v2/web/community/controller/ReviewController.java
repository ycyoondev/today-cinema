package com.todaycinema.v2.web.community.controller;

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

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewsResponseDto> getReviews(@PathVariable("movieId") long movieId, Authentication authentication) {
        return ResponseEntity.ok(reviewService.getReviews(movieId, authentication));
    }

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable("movieId") long movieId,
            Authentication authentication,
            @RequestBody ReviewRequestDto reviewRequestDto) {
        return ResponseEntity.ok(reviewService.createReview(movieId, authentication, reviewRequestDto));
    }
}
