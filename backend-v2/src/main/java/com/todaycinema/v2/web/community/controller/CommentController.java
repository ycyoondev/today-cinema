package com.todaycinema.v2.web.community.controller;

import com.todaycinema.v2.web.community.dto.CommentsResponseDto;
import com.todaycinema.v2.web.community.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
