package com.zarebin.dto;

import lombok.Data;


@Data
public class PdfRequest {
    private Long inspectionId;
    private String inspectionDate;
    private String inspectionTime;
    private String fullName;
    private String phoneNumber;

    private String carName;
    private String carModel;

    private String chassisNumber;

    private String plateNumber;

    private String expertName;

    private String expertPhoneNumber;

    private String expertDescription;

    // Body panels
    private Boolean roof;
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
}
