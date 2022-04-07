package com.todaycinema.v2.web.dbcontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbGenreDTO {

    @JsonProperty(value = "id")
    private Long tmdbId;
    private String name;

}
