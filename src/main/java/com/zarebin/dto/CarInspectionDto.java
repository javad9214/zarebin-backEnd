package com.zarebin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInspectionDto {

    private Long id;

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

    @NotBlank(message = "Chassis number is required")
    private String chassisNumber;

    @NotBlank(message = "Plate number is required")
    private String plateNumber;

    private String expertName;

    private String expertPhoneNumber;

    private String expertDescription;

    // Body panels
    private Boolean roof;
    private Boolean chassis;
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
