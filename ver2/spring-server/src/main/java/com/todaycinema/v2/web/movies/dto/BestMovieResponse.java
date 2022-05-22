package com.todaycinema.v2.web.movies.dto;

import com.todaycinema.v2.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BestMovieResponse {
    private List<MovieDetailResponse> best = new ArrayList<>();
    private Map<String, Object> genre_best1 = new HashMap<>();
    private Map<String, Object> genre_best2 = new HashMap<>();
}
