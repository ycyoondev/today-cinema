package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Genre {
    @Id
    @Column(name = "genre_id")
    private Long id;

    private String name;

    @Column(name = "tmdb_id")
    private Long tmdbId;
}
