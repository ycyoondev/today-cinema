package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.config.WebClientConfig;
import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.MovieRecommendUser;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.MovieRecommendUserRepository;
import com.todaycinema.v2.domain.repository.MovieRepositoryDataJpa;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.movies.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {
    private final MovieRepositoryDataJpa movieRepositoryDataJpa;
    private final MovieRecommendUserRepository movieRecommendUserRepository;
    private final UserRepository userRepository;
    private final WebClientConfig webClientConfig;

    public TournamentMoviesResponse getTournament(int num) {
        List<Movie> movies = movieRepositoryDataJpa.findAllOrderByRand(num);
        TournamentMoviesResponse tournamentMoviesResponseDto = new TournamentMoviesResponse();
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
        return tournamentMoviesResponseDto;
    }

    @Transactional
    public TournamentMoviesResponse recommendMovie(Long movieId, Authentication authentication) {
        // 유저 저장
        User user = userRepository.findByUsername(authentication.getName()).get();
        // 추천 서버에서 영화 id 받기
        WebClient client = webClientConfig.webClientRecommend();
        RecommendMovieIdDto recommendMovieIdDto = client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/movies/recommends/" + movieId.toString() + "/")
                        .build()
                ).retrieve().bodyToMono(RecommendMovieIdDto.class).block();
        // 영화 양식에 맞게 조회해서 보내기
        List<Long> recommendMovieIds = recommendMovieIdDto.getRecommendMovie();
        if (recommendMovieIds.isEmpty()) {
            List<Movie> randMovies = movieRepositoryDataJpa.findAllOrderByRand(5);
            for (Movie randMovie : randMovies) {
                recommendMovieIds.add(randMovie.getId());
            }
            log.info("랜덤영화: " + recommendMovieIds.toString());
        } else {
            log.info("추천영화: " + recommendMovieIds.toString());
        }
        TournamentMoviesResponse tournamentMoviesResponseDto = new TournamentMoviesResponse();
        for (Long recommendMovieId : recommendMovieIds) {
            Movie movie = movieRepositoryDataJpa.findById(recommendMovieId).get();
            MovieRecommendUser movieRecommendUser = new MovieRecommendUser();
            movieRecommendUser.setMovie(movie);
            movieRecommendUser.setUser(user);
            movieRecommendUserRepository.save(movieRecommendUser);
            TournamentMovieDto tournamentMovieDto = new TournamentMovieDto(
                    movie.getId(), movie.getTitle(), movie.getTmdbRating(), movie.getOverview(), movie.getPosterPath()
            );
            tournamentMoviesResponseDto.getMovies().add(tournamentMovieDto);
        }
        return tournamentMoviesResponseDto;
    }

    @Transactional
    public MessageResponse addRecommendwithUser(Long movieId, Authentication authentication) {
        return null;
    }
}
