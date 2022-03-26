package com.todaycinema.v2.movies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Genre {
    @Id
    private Long id;
    private String name;
    private Long tmdbId;
}
