package com.todaycinema.v2.web.dbcontrol.service;

import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.repository.GenreRepository;
import com.todaycinema.v2.domain.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@SpringBootTest
@Transactional
class UpdateDBServiceTest {

    @Autowired
    UpdateDBService updateDBService;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    MovieRepository movieRepository;

    @Test
    void TMDB로부터장르저장() {
        // 로직 수행
        updateDBService.updateGenre();
        // 결과 확인
        List<Genre> genres = genreRepository.findAll();
        Assertions.assertNotEquals(genres.size(), 0);
    }

    @Test
    void TMDB로부터영화저장() {
        // 로직 수행
        updateDBService.updateMovie();
        // 결과 확인
        List<Movie> movies = movieRepository.findAll();
        Assertions.assertNotEquals(movies.size(), 0);
    }
}