package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Movie {

    @Id @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "tmdb_rating")
    private float tmdbRating;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(name = "video_key")
    private String videoKey;

    @Column(name = "poster_path")
    private String posterPath;

    private boolean adult;

}
