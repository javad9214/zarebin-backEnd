package com.zarebin.dto;

import lombok.Data;

@Data
public class PdfRequest {
    private Long inspectionId;
    private String fullName;
    private String phoneNumber;
    private String carName;
    private String carModel;
    private String plateNumber;
    private String vin;
    private String ownerName;
    private String engineNumber;
    private String chassisNumber;
    private String inspectionDate; // yyyy-MM-dd

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
    private Boolean frontBumper;
    private Boolean rearBumper;
}
