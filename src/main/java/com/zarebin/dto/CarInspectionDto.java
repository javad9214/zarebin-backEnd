package com.zarebin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInspectionDto {

    private Long id;

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

    private String expertDescription;

    // Body panels
    private String frontBumper;
    private String hood;
    private String frontRightFender;
    private String frontRightDoor;
    private String rearRightDoor;
    private String rearRightFender;
    private String trunkLid;
    private String rearLeftFender;
    private String rearLeftDoor;
    private String frontLeftDoor;
    private String frontLeftFender;
    private String roof;
    private String rearBumper;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
