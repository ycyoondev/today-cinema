package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import com.todaycinema.v2.web.movies.service.BestMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class BestMovieController {

    public final BestMovieService bestMovieService;

    @GetMapping("/best")
    public BestMovieResponse best() {
        List<Movie> bestMovie = bestMovieService.findBestMovie(20);
        BestMovieResponse bestMovieResponse = new BestMovieResponse(bestMovie);
        return bestMovieResponse;
    }

}
