package com.zarebin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Structured Iranian plate on the API: {"firstTwo":"12","letter":"ب",
 * "threeDigits":"345","iranCode":"67"}. The fronts render these parts
 * directly; the flat-string format is no longer sent over the wire.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlateNumberDto {

    @NotBlank(message = "Plate first two digits are required")
    @Size(min = 2, max = 2, message = "Plate first part must be exactly 2 digits")
    private String firstTwo;

    @NotBlank(message = "Plate letter is required")
    @Size(min = 1, max = 1, message = "Plate letter must be exactly 1 character")
    private String letter;

    @NotBlank(message = "Plate three digits are required")
    @Size(min = 3, max = 3, message = "Plate middle part must be exactly 3 digits")
    private String threeDigits;

    @NotBlank(message = "Plate iran code is required")
    @Size(min = 2, max = 2, message = "Plate iran code must be exactly 2 digits")
    private String iranCode;
}
