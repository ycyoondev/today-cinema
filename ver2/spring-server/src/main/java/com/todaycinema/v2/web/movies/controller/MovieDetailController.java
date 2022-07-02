package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.web.movies.dto.MovieDetailResponse;
import com.todaycinema.v2.web.movies.service.MovieDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "movies")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class MovieDetailController {

    public final MovieDetailService movieDetailService;

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailResponse> movieDetail(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieDetailService.movieDetail(movieId));
    }
}
