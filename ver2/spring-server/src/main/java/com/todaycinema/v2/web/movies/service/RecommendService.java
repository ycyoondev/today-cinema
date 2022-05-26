package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.config.WebClientConfig;
import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.MovieRepositoryDataJpa;
import com.todaycinema.v2.web.dbcontrol.dto.TmdbGenresDTO;
import com.todaycinema.v2.web.movies.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {
    private final MovieRepositoryDataJpa movieRepositoryDataJpa;
    private final WebClientConfig webClientConfig;

    public ResponseEntity<TournamentMoviesResponseDto> getTournament(int num) {
        List<Movie> movies = movieRepositoryDataJpa.findAllOrderByRand(num);
        TournamentMoviesResponseDto tournamentMoviesResponseDto = new TournamentMoviesResponseDto();
        for (Movie movie : movies) {
            TournamentMovieDto tournamentMovieDto = new TournamentMovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getTmdbRating(),
                    movie.getOverview(),
                    movie.getPosterPath()
            );
            tournamentMoviesResponseDto.getMovies().add(tournamentMovieDto);
        }
        return ResponseEntity.ok(tournamentMoviesResponseDto);
    }

    public ResponseEntity<RecommendMovieResponseDto> recommendMovie(Long movieId, Authentication authentication) {
        // 추천 서버에서 영화 id 받기
        WebClient client = webClientConfig.webClientRecommend();
        RecommendMovieIdDto recommendMovieIdDto = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies/recommends/" + movieId.toString() + "/")
                        .build()
                ).retrieve().bodyToMono(RecommendMovieIdDto.class).block();
        // 영화 양식에 맞게 조회해서 보내기
        List<Long> recommendMovieIds = recommendMovieIdDto.getRecommendMovie();
        return ResponseEntity.ok(new RecommendMovieResponseDto(recommendMovieIds));
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> addRecommendwithUser(Long movieId, Authentication authentication) {

        return ResponseEntity.ok(null);
    }
}
