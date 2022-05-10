package com.todaycinema.v2.web.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MovieDetailMiniDto {

    private Long id;
    private String title;
    @JsonProperty(value = "tmdb_rating")
    private float tmdbRating;
    @JsonProperty(value = "poster_path")
    private String posterPath;
}
