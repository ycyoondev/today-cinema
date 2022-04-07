package com.todaycinema.v2.web.dbcontrol.service;

import com.todaycinema.v2.config.WebClientConfig;
import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.web.dbcontrol.dto.TmdbGenreDTO;
import com.todaycinema.v2.web.dbcontrol.dto.TmdbGenresDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public void updateGenre() {
        // 장르 채우기
        String apiKey = "e47790c923409dca4c2e985789776181";
        genreRepository.turncateGenre();

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


}
