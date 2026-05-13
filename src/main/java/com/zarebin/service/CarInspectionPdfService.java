package com.zarebin.service;

import com.zarebin.dto.PdfRequest;
import com.zarebin.dto.PdfResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class CarInspectionPdfService {

    private final WebClient pdfWebClient;

    public PdfResponse sendToPython(PdfRequest request) {
        return pdfWebClient.post()
                .uri("/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PdfResponse.class)
                .block();
    }
}
