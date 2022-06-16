package com.todaycinema.v2.apitest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class WebClientTest {

    @Test
    @DisplayName("WebClient connect test: Success")
    void doTest() {
        WebClient client = WebClient.create();
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        Mono<String> stringMono = client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        Assertions.assertThat(stringMono).isNotNull();
    }
}
