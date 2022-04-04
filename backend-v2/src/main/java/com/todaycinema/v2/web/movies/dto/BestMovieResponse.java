package com.todaycinema.v2.web.movies.dto;

import com.todaycinema.v2.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BestMovieResponse {
    private List<Movie> movies;
}
