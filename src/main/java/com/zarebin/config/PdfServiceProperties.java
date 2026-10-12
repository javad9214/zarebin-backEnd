package com.zarebin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * External PDF-generator service settings ("app.pdf-service.*" in application.yml).
 */
@ConfigurationProperties(prefix = "app.pdf-service")
public record PdfServiceProperties(String baseUrl) {
}
