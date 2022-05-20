package com.todaycinema.v2.web.dbcontrol.dto;

import lombok.Data;

import java.util.List;

@Data
public class TmdbVideosDTO {
    private Long id;
    private List<TmdbVideoDTO> results;
}
