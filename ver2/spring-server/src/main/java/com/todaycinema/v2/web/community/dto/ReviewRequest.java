package com.todaycinema.v2.web.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private String content;
    @JsonProperty("user_rating")
    private int userRating;
    @JsonProperty("is_spoiler_self")
    private boolean isSpoilerSelf;
}
