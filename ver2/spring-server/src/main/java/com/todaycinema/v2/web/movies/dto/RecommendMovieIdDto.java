package com.todaycinema.v2.web.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendMovieIdDto {
    private List<Long> recommendMovie;
}
