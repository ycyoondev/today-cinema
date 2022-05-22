package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.domain.repository.GenreRepositoryDataJpa;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.domain.repository.MovieRepositoryDataJpa;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import com.todaycinema.v2.web.movies.dto.MovieDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BestMovieService {

    private final MovieRepository movieRepository;
    private final MovieRepositoryDataJpa movieRepositoryDataJpa;
    private final GenreRepository genreRepository;
    private final GenreRepositoryDataJpa genreRepositoryDataJpa;


    public List<Movie> findBestGenreMovie(int num, Long genreId) {
        List<Movie> bestGenreMovie = movieRepository.findTopNumByGenre(num, genreId);
        return bestGenreMovie;
    }


    public BestMovieResponse getBestMovies() {
        BestMovieResponse bestMovieResponse = new BestMovieResponse();
        // Best영화
        List<Movie> bestMovie = movieRepository.findTopNumByTmdbRating(20);
        bestMovieResponse.setBest(convertMovieToDto(bestMovie));
        // Best장르 영화
        List<Long> genreIds = genreRepositoryDataJpa.findAllId();
        Random random = new Random();
        Long randomId1 = genreIds.get(random.nextInt(genreIds.size()));
        Long randomId2 = genreIds.get(random.nextInt(genreIds.size()));
        while (randomId1.equals(randomId2)) {
            randomId1 = genreIds.get(random.nextInt(genreIds.size()));
            randomId2 = genreIds.get(random.nextInt(genreIds.size()));
        }
        log.info("선택된 ID: {} {}", randomId1, randomId2);
        String genreName1 = genreRepositoryDataJpa.findById(randomId1).get().getName();
        String genreName2 = genreRepositoryDataJpa.findById(randomId2).get().getName();
        List<Movie> bestGenreMovie1 = movieRepositoryDataJpa.findByGenresInOrderByTmdbRatingDesc(randomId1);
        Map<String, Object> genreBest1 = new HashMap<>();
        genreBest1.put("genre", genreName1);
        genreBest1.put("data", convertMovieToDto(bestGenreMovie1));
        bestMovieResponse.setGenre_best1(genreBest1);
        List<Movie> bestGenreMovie2 = movieRepositoryDataJpa.findByGenresInOrderByTmdbRatingDesc(randomId2);
        Map<String, Object> genreBest2 = new HashMap<>();
        genreBest2.put("genre", genreName2);
        genreBest2.put("data", convertMovieToDto(bestGenreMovie2));
        bestMovieResponse.setGenre_best2(genreBest2);
        return bestMovieResponse;
    }

    private List<MovieDetailResponse> convertMovieToDto(List<Movie> bestMovie) {
        List<MovieDetailResponse> movieDetailResponses = new ArrayList<>();
        for (Movie movie : bestMovie) {
            MovieDetailResponse movieDetailResponse = new MovieDetailResponse(
                    movie.getId(),
                    movie.getGenres(),
                    movie.getReleaseDate(),
                    movie.getTitle(),
                    movie.getTmdbRating(),
                    movie.getOverview(),
                    movie.getPosterPath(),
                    movie.getTmdbId(),
                    movie.getVideoKey(),
                    movie.isAdult()
            );
            movieDetailResponses.add(movieDetailResponse);
        }
        return movieDetailResponses;
    }

}
