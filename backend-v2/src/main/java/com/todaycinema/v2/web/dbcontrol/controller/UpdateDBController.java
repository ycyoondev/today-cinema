package com.todaycinema.v2.web.dbcontrol.controller;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.web.dbcontrol.service.UpdateDBService;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class UpdateDBController {
    private final UpdateDBService updateDBService;

    @GetMapping("/update-movies")
    public void updateDB() {
        updateDBService.updateGenre();
        updateDBService.updateMovie();
    }
}
