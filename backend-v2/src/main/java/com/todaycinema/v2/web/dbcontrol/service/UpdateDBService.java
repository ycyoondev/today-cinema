package com.todaycinema.v2.web.dbcontrol.service;

import com.todaycinema.v2.config.WebClientConfig;
import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.MovieGenre;
import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.web.dbcontrol.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateDBService {

    private final WebClientConfig webClientConfig;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    @Value("${tmdb_key}")
    private String apiKey;

    @Transactional
    public void updateGenre() {
        // 장르 채우기
        genreRepository.truncateGenre();

        WebClient webClient = webClientConfig.webClientTMDB();
        TmdbGenresDTO genres = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/genre/movie/list")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko-KR")
                        .build()
                ).retrieve().bodyToMono(TmdbGenresDTO.class).block();
        for (TmdbGenreDTO genreDTO : genres.getGenres()) {
            Genre genre = new Genre();
            genre.setTmdbId(genreDTO.getTmdbId());
            genre.setName(genreDTO.getName());
            genreRepository.save(genre);
        }
    }

    @Transactional
    public void updateMovie() {
        // 영화 채우기
        movieRepository.truncateMovie(); //

        WebClient webClient = webClientConfig.webClientTMDB();
        for (int i=1; i < 100; i++) {
            String pageNum = Integer.toString(i);
            TmdbMoviesDTO movies = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/movie/popular")
                            .queryParam("api_key", apiKey)
                            .queryParam("language", "ko-KR")
                            .queryParam("page", pageNum)
                            .build()
                    ).retrieve().bodyToMono(TmdbMoviesDTO.class).block();
            for (TmdbMovieDTO movieDTO : movies.getResults()) {
                Movie movie = new Movie();
                movie.setReleaseDate(movieDTO.getReleaseDate());
                movie.setTitle(movieDTO.getTitle());
                movie.setTmdbRating(movieDTO.getTmdbRating());
                movie.setOverview(movieDTO.getOverview());
                movie.setPosterPath(movieDTO.getPosterPath());
                movie.setTmdbId(movieDTO.getTmdbId());
                movie.setAdult(movieDTO.isAdult());
                movie.setVideoKey(getVideoKey(movieDTO.getTmdbId()));

                Integer[] ids = movieDTO.getGenreIds().toArray(new Integer[0]);
                movie.setGenres(genreRepository.makeGenreList(ids));
                movieRepository.save(movie);
            }
        }
        log.info("movie가 업데이트 되었습니다.");

    }

    public String getVideoKey(Long tmdbId) {
        String movieId = String.valueOf(tmdbId);
        WebClient webClient = webClientConfig.webClientTMDB();
        TmdbVideosDTO videosDTO = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/" + movieId + "/videos")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "ko-KR")
                        .build()
                ).retrieve().bodyToMono(TmdbVideosDTO.class).block();
        if (videosDTO == null) {
            return "no video";
        }
        for (TmdbVideoDTO videoDTO : videosDTO.getResults()) {
            if (videoDTO.getKey().equals("Trailer")) {
                return videoDTO.getKey();
            }
        }
        for (TmdbVideoDTO videoDTO : videosDTO.getResults()) {
            if (videoDTO.getType().equals("Teaser")) {
                return videoDTO.getKey();
            }
        }
        if (!videosDTO.getResults().isEmpty()) {
            return videosDTO.getResults().get(0).getKey();
        }
        return "no video";
    }

}
