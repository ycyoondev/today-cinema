package com.todaycinema.v2.web.movies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TournamentMovieDto {
    private Long id;
    private String title;
    @JsonProperty(value = "tmdb_rating")
    private float tmdbRating;
    private String overview;
    @JsonProperty(value = "poster_path")
    private String posterPath;
}
