package com.todaycinema.v2.web.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private long id;
    @JsonProperty("user")
    private UserMiniDto userMiniDto;
    private String content;
    @JsonProperty("user_rating")
    private int userRating;
    @JsonProperty("is_spoiler_self")
    private boolean isSpoilerSelf;
    @JsonProperty("is_spoiler_checked")
    private boolean isSpoilerChecked;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updated_at;
    @JsonProperty("like_users")
    private List<Long> likeUsers = new ArrayList<>();
    @JsonProperty("spoiler_check_users")
    private List<Long> spoilerCheckUsers = new ArrayList<>();

    public ReviewResponse(long id, UserMiniDto userMiniDto, String content, int userRating, boolean isSpoilerSelf, boolean isSpoilerChecked, LocalDateTime createdAt, LocalDateTime updated_at) {
        this.id = id;
        this.userMiniDto = userMiniDto;
        this.content = content;
        this.userRating = userRating;
        this.isSpoilerSelf = isSpoilerSelf;
        this.isSpoilerChecked = isSpoilerChecked;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
    }
}
