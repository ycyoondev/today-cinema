package com.todaycinema.v2.web.dbcontrol.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbGenresDTO {
    private List<TmdbGenreDTO> genres;
}
