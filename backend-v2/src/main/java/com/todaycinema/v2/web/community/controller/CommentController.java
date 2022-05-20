package com.todaycinema.v2.web.community.controller;

import com.todaycinema.v2.web.community.dto.CommentRequestDto;
import com.todaycinema.v2.web.community.dto.CommentResponseDto;
import com.todaycinema.v2.web.community.dto.CommentsResponseDto;
import com.todaycinema.v2.web.community.service.CommentService;
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
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{movieId}/review/{reviewId}/comments")
    public ResponseEntity<CommentsResponseDto> getComments(
            @PathVariable("movieId") Long movieId,
            @PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.ok(commentService.getComments(movieId, reviewId));
    }

    @PostMapping("/{movieId}/review/{reviewId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable("movieId") Long movieId,
            @PathVariable("reviewId") Long reviewId,
            Authentication authentication,
            @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.createComment(movieId, reviewId, authentication, commentRequestDto));
    }
}
