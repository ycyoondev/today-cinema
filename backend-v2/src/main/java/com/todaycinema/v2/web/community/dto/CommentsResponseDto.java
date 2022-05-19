package com.todaycinema.v2.web.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsResponseDto {
    private List<CommentResponseDto> comments = new ArrayList<>();
}
