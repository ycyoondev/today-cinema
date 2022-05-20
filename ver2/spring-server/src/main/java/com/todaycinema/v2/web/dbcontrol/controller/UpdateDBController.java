package com.todaycinema.v2.web.dbcontrol.controller;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.web.dbcontrol.service.UpdateDBService;
import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "DBcontrol")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class UpdateDBController {
    private final UpdateDBService updateDBService;

    @GetMapping("/update-movies")
    public String updateDB() {
        updateDBService.updateGenre();
        updateDBService.updateMovie();
        return "DB가 업데이트되었습니다.";
    }
}
