package com.zarebin.service;

import com.zarebin.dto.PdfRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class CarInspectionPdfService {

    private final WebClient pdfWebClient;

    public String sendToPython(PdfRequest request) {
        return pdfWebClient.post()
                .uri("/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
