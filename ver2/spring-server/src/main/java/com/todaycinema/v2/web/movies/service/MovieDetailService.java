package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieDetailService {
    private final MovieRepository movieRepository;

    public Movie movieDetail(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        return movie;
    }
}
