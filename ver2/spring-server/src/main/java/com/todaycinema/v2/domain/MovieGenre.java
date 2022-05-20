package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Entity
//@Getter
//@Setter
public class MovieGenre {
    // ManyToMany로 사용
    @Id
    @GeneratedValue
    @Column(name = "movie_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
