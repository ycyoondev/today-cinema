package com.todaycinema.v2.web.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.web.movies.dto.MovieDetailResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    private long id;
    private String username;
    private String introduction;
    private List<UserMiniDto> followings = new ArrayList<>();
    private List<UserMiniDto> followers = new ArrayList<>();
    private List<UserMiniDto> blockings = new ArrayList<>();
    @JsonProperty("wish_movies")
    private List<MovieDetailMiniDto> wishMovies = new ArrayList<>();
    @JsonProperty("recommend_movies")
    private List<MovieDetailMiniDto> recommendMovies = new ArrayList<>();
}
/*
{
  "id": 20,
  "last_login": null,
  "username": "auth123",
  "date_joined": "2022-05-09T21:10:38.556165+09:00",
  "introduction": "",
  "followings": [
    {
      "id": 3,
      "username": "auth3"
    }
  ],
  "followers": [],
  "blockings": [],
  "wish_movies": [
    {
      "id": 132,
      "title": "너의 이름은.",
      "tmdb_rating": "8.6",
      "poster_path": "/wx1Dxr4UyvN18SyC5GsVWWWtYja.jpg"
    }
  ],
  "recommend_movies": []
}
*
* */