package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.web.movies.dto.MessageResponseDto;
import com.todaycinema.v2.web.movies.dto.TournamentMoviesResponseDto;
import com.todaycinema.v2.web.movies.service.RecommendService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "movies")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/movies")
public class RecommendController {
    private final RecommendService recommendService;

    /**
     * num 개의 랜덤 영화 조회
     * @param num
     */
    @GetMapping("/tournament/{num}")
    public ResponseEntity<TournamentMoviesResponseDto> getTournament(@PathVariable("num") int num) {
        return recommendService.getTournament(num);
    }

    /**
     * 추천 영화 5개 조회
     * @param movieId
     * @param authentication
     */
    @PostMapping("/tournament/{movieId}")
    public ResponseEntity<TournamentMoviesResponseDto> recommendMovie(
            @PathVariable("movieId") Long movieId,
            Authentication authentication){
        return recommendService.recommendMovie(movieId, authentication);
    }

    /**
     * 추천 영화 개인 기록
     * @param movieId
     * @param authentication
     */
    @PostMapping("/tournament/user/{movieId}")
    public ResponseEntity<MessageResponseDto> addRecommendwithUser(
            @PathVariable("movieId") Long movieId,
            Authentication authentication){
        return recommendService.addRecommendwithUser(movieId, authentication);
    }
}
