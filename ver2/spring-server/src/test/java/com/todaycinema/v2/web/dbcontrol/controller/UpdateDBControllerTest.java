package com.todaycinema.v2.web.dbcontrol.controller;

import com.todaycinema.v2.web.dbcontrol.dto.DbResultResponse;
import com.todaycinema.v2.web.dbcontrol.service.UpdateDBService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Update DB Controller Test")
class UpdateDBControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Update Db: Fail: 401")
    @Transactional
    void updateDbUnauthorized() {
        // given
        String url = "http://localhost:" + port + "/v2/update-movies/1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        // when
        ResponseEntity<DbResultResponse> response = restTemplate.postForEntity(url, request, DbResultResponse.class);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}