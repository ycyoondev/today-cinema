package com.todaycinema.v2.web.community.controller;

import com.todaycinema.v2.web.community.dto.CommentRequest;
import com.todaycinema.v2.web.community.dto.CommentResponse;
import com.todaycinema.v2.web.community.dto.CommentsResponse;
import com.todaycinema.v2.web.community.dto.MessageResponse;
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

    /**
     * 덧글 전체 조회
     * @param movieId
     * @param reviewId
     */
    @GetMapping("/{movieId}/review/{reviewId}/comments")
    public ResponseEntity<CommentsResponse> getComments(
            @PathVariable("movieId") Long movieId,
            @PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.ok(commentService.getComments(movieId, reviewId));
    }

    /**
     * 덧글 생성
     * @param movieId
     * @param reviewId
     * @param authentication
     * @param commentRequest
     */
    @PostMapping("/{movieId}/review/{reviewId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("movieId") Long movieId,
            @PathVariable("reviewId") Long reviewId,
            Authentication authentication,
            @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(movieId, reviewId, authentication, commentRequest));
    }

    @DeleteMapping("/{movieId}/review/{reviewId}/comment/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable("movieId") Long movieId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.deleteComment(movieId, reviewId, commentId));
    }
}
