package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.web.movies.dto.MovieDetailResponse;
import com.todaycinema.v2.web.movies.service.MovieDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Best Detail Controller Test")
class MovieDetailControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieDetailService movieDetailService;

    @Test
    @DisplayName("get Movie Detail: Success")
    void movieDetail() throws Exception {
        // given
        MovieDetailResponse movieDetailResponse = new MovieDetailResponse();
        given(movieDetailService.movieDetail(anyLong())).willReturn(movieDetailResponse);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .get("/v2/movies/100"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }
}