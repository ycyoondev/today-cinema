package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.web.movies.dto.MovieWishResponse;
import com.todaycinema.v2.web.movies.service.MovieUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "movies")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class MovieUserController {
    private final MovieUserService movieUserService;

    @PostMapping("/wish/{movieId}")
    public ResponseEntity<MovieWishResponse> postWishMovie(
            @PathVariable("movieId") long movieId,
            Authentication authentication) {
        return ResponseEntity.ok(movieUserService.postWishMovie(movieId, authentication));
    }
}
