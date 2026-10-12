package com.zarebin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(PdfServiceProperties.class)
public class PdfClientConfig {

    @Bean
    public WebClient pdfWebClient(PdfServiceProperties properties) {
        return WebClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }
}
