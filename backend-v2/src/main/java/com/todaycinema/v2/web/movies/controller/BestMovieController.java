package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import com.todaycinema.v2.web.movies.service.BestMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class BestMovieController {

    public final BestMovieService bestMovieService;
    public final GenreRepository genreRepository;

    @GetMapping("/best")
    public BestMovieResponse best() {
        List<Movie> best = bestMovieService.findBestMovie(20);
        List<Long> genreIds = bestMovieService.findGenreIds();
        Random random = new Random();
        Long randomId1 = genreIds.get(random.nextInt(genreIds.size()));
        Long randomId2 = genreIds.get(random.nextInt(genreIds.size()));
        log.info("선택된 ID: {} {}", randomId1, randomId2);
        List<Movie> genreBest1 = bestMovieService.findBestGenreMovie(20, randomId1);
        String genreName1 = genreRepository.findGenreName(randomId1);
        String genreName2 = genreRepository.findGenreName(randomId2);
        List<Movie> genreBest2 = bestMovieService.findBestGenreMovie(20, randomId2);
        BestMovieResponse bestMovieResponse = new BestMovieResponse(best, genreBest1, genreBest2, genreName1, genreName2);
        return bestMovieResponse;
    }

    @GetMapping("/{genreId}/best")
    public List<Movie> genreBest(@PathVariable Long genreId) {
        List<Movie> bestGenreMovie = bestMovieService.findBestGenreMovie(20, genreId);
        return bestGenreMovie;
    }

}
