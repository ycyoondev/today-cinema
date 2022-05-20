package com.todaycinema.v2.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
