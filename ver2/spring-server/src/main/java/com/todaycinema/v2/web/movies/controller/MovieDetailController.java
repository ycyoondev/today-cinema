package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.web.movies.dto.MovieDetailResponse;
import com.todaycinema.v2.web.movies.service.MovieDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "movies")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class MovieDetailController {

    public final MovieDetailService movieDetailService;

    @GetMapping("/{movieId}")
    public MovieDetailResponse movieDetail(@PathVariable Long movieId) {
        Movie movie = movieDetailService.movieDetail(movieId);
        MovieDetailResponse movieDetailResponse = new MovieDetailResponse();
        movieDetailResponse.setMovieId(movieId);
        movieDetailResponse.setGenres(movie.getGenres());
        movieDetailResponse.setReleaseDate(movie.getReleaseDate());
        movieDetailResponse.setTitle(movie.getTitle());
        movieDetailResponse.setTmdbRating(movie.getTmdbRating());
        movieDetailResponse.setOverview(movie.getOverview());
        movieDetailResponse.setPosterPath(movie.getPosterPath());
        movieDetailResponse.setPosterPath(movie.getPosterPath());
        movieDetailResponse.setTmdbId(movie.getTmdbId());
        movieDetailResponse.setVideoKey(movie.getVideoKey());
        movieDetailResponse.setAdult(movie.isAdult());
        return movieDetailResponse;
    }
}
