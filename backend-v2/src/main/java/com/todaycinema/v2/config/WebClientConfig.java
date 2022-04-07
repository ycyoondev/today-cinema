package com.todaycinema.v2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClientTMDB(){
        String baseUri = "https://api.themoviedb.org/3";
        return WebClient.create(baseUri);
    }
}
