package com.todaycinema.v2.web.movies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.domain.Genre;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDetailResponse {

    private Long movieId;
    private List<Genre> genres;
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;
    private String title;
    @JsonProperty(value = "tmdb_rating")
    private float tmdbRating;
    private String overview;
    @JsonProperty(value = "poster_path")
    private String posterPath;
    @JsonProperty(value = "tmdb_id")
    private Long tmdbId;
    @JsonProperty(value = "video_key")
    private String videoKey;
    private boolean adult;

}
