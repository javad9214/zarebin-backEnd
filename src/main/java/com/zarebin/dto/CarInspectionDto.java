package com.zarebin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInspectionDto {

    private Long id;

    /**
     * Unguessable share key for the web report link; assigned by the server,
     * ignored on create/update input.
     */
    private UUID publicId;

    @NotNull(message = "Inspection date is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime inspectionDate;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Car name is required")
    private String carName;

    @NotBlank(message = "Car model is required")
    private String carModel;

    /**
     * Optional: stored as null when the expert leaves it blank (the app
     * sends null for an empty field).
     */
    private String chassisNumber;

    @NotNull(message = "Plate number is required")
    @Valid
    private PlateNumberDto plateNumber;

    private String expertName;

    private String expertPhoneNumber;

    private String expertDescription;

    // Body panels
    private Boolean roof;
    // Split chassis + pillars since Oct 2026; null on rows created before them.
    private Boolean frontChassis;
    private Boolean rearChassis;
    private Boolean pillars;
    private Boolean frontLeftFender;
    private Boolean frontRightFender;
    private Boolean rearLeftFender;
    private Boolean rearRightFender;
    private Boolean hood;
    private Boolean trunk;
    private Boolean frontLeftDoor;
    private Boolean frontRightDoor;
    private Boolean rearLeftDoor;
    private Boolean rearRightDoor;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
