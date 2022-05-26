package com.todaycinema.v2.web.movies.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendMovieIdDto {
    private List<Long> recommendMovie;
}
