package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BestMovieService {

    public final MovieRepository movieRepository;

    @Transactional
    public List<Movie> findBestMovie(int num) {
        List<Movie> bestMovie = movieRepository.findTopNumByTmdbRating(num);
        return bestMovie;
    }

    @Transactional
    public List<Movie> findBestGenreMovie(int num, Long genreId) {
        List<Movie> bestGenreMovie = movieRepository.findTopNumByGenre(num, genreId);
        return bestGenreMovie;
    }
}
