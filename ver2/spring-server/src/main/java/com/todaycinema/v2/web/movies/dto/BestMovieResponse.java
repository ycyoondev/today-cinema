package com.todaycinema.v2.web.movies.dto;

import com.todaycinema.v2.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BestMovieResponse {
    private List<Movie> best;
    private Map<String, Object> genre_best1 = new HashMap<>();
    private Map<String, Object> genre_best2 = new HashMap<>();

    public BestMovieResponse(List<Movie> best, List<Movie> genre_best1, List<Movie> genre_best2, String genreName1, String genreName2) {
        this.best = best;
        this.genre_best1.put("data", genre_best1);
        this.genre_best1.put("genre", genreName1);
        this.genre_best2.put("data", genre_best2);
        this.genre_best2.put("genre", genreName2);
    }
}
