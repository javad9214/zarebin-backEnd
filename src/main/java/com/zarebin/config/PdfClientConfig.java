package com.zarebin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PdfClientConfig {

    @Bean
    public WebClient pdfWebClient() {
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:8082")
                .build();
    }
}
