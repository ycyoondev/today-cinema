package com.todaycinema.v2.web.movies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.Movie;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MovieDetailResponse {

    @JsonProperty(value = "id")
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

    public static MovieDetailResponse toDto(Movie movie) {
        return MovieDetailResponse.builder()
                .movieId(movie.getId())
                .genres(movie.getGenres())
                .releaseDate(movie.getReleaseDate())
                .title(movie.getTitle())
                .tmdbRating(movie.getTmdbRating())
                .overview(movie.getOverview())
                .posterPath(movie.getPosterPath())
                .tmdbId(movie.getTmdbId())
                .videoKey(movie.getVideoKey())
                .adult(movie.isAdult())
                .build();
    }
}
