package com.todaycinema.v2.web.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendMovieResponseDto {
    private List<Long> movieIds;
}
