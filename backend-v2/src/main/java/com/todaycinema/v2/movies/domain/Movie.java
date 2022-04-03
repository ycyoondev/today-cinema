package com.todaycinema.v2.movies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Long id;

    private String title;

    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Column(name = "tmdb_rating")
    private float tmdbRating;

    private String text;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(name = "video_key")
    private String videoKey;

    @Column(name = "poster_path")
    private String posterPath;

    private boolean adult;

}
