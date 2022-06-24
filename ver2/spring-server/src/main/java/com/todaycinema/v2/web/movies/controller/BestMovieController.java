package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import com.todaycinema.v2.web.movies.service.BestMovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "movies")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class BestMovieController {

    public final BestMovieService bestMovieService;
    public final GenreRepository genreRepository;

    @GetMapping("/best")
    public ResponseEntity<BestMovieResponse> best() {
        return ResponseEntity.ok(bestMovieService.getBestMovies());
    }
}
