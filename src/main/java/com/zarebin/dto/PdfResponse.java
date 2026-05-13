package com.zarebin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfResponse {
    private String status;
    private String message;
    @JsonProperty("download_url")
    private String downloadUrl;
    private String filename;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("file_size")
    private Long fileSize;
}
