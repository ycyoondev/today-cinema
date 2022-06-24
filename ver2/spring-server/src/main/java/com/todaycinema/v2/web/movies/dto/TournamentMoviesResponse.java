package com.todaycinema.v2.web.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TournamentMoviesResponse {
    private List<TournamentMovieDto> movies = new ArrayList<>();
}
