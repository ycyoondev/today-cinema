package com.todaycinema.v2.web.dbcontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TmdbMovieDTO {

    private boolean adult;
    private String backdrop_path;
    @JsonProperty(value = "genre_ids")
    private List<Integer> genreIds;
    @JsonProperty(value = "id")
    private Long tmdbId;
    private String title;
    @JsonProperty(value = "release_date")
    private LocalDate releaseDate;
    @JsonProperty(value = "vote_average")
    private float tmdbRating;
    private String overview;
    @JsonProperty(value = "poster_path")
    private String posterPath;

}
