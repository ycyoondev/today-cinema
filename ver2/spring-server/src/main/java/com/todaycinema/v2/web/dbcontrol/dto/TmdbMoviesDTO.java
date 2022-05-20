package com.todaycinema.v2.web.dbcontrol.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbMoviesDTO {
    private Integer page;
    private List<TmdbMovieDTO> results;
}
