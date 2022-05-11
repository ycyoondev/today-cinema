package com.todaycinema.v2.web.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewsResponseDto {
    private List<ReviewResponseDto> reviews = new ArrayList<>();
}
