package com.todaycinema.v2.web.movies.controller;

import com.todaycinema.v2.web.movies.dto.BestMovieResponse;
import com.todaycinema.v2.web.movies.service.BestMovieService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Best Movie Controller Test")
class BestMovieControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BestMovieService bestMovieService;

    @Test
    @DisplayName("get best movie: Success")
    void best() throws Exception {
        // given
        BestMovieResponse bestMovieResponse = new BestMovieResponse();
        given(bestMovieService.getBestMovies()).willReturn(bestMovieResponse);

        // when
        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .get("/v2/movies/best"));

        // then
        actions.andDo(print())
                .andExpect(status().isOk());
    }
}