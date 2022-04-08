package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Genre {
    @Id @GeneratedValue
    @Column(name = "genre_id")
    private Long id;

    private String name;

    @Column(name = "tmdb_id")
    private Long tmdbId;

}
