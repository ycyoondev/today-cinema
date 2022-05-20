package com.todaycinema.v2.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 외래키
    @ManyToMany
    @JoinTable(name = "Movie_Genre",
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();
}
