package com.todaycinema.v2.web.community.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private UserMiniDto user;
    private String content;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updated_at;
}
