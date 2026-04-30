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
}
