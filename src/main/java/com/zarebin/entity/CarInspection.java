package com.zarebin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_inspections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String carName;

    @Column(nullable = false)
    private String carModel;

    @Column(nullable = false)
    private String chassisNumber;

    @Column(nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private LocalDateTime inspectionDate;

    @Column(columnDefinition = "TEXT")
    private String expertDescription;

    // Body panels - Boolean fields
    private Boolean frontBumper;
    private Boolean hood;
    private Boolean frontRightFender;
    private Boolean frontRightDoor;
    private Boolean rearRightDoor;
    private Boolean rearRightFender;
    private Boolean trunk;
    private Boolean rearLeftFender;
    private Boolean rearLeftDoor;
    private Boolean frontLeftDoor;
    private Boolean frontLeftFender;
    private Boolean roof;
    private Boolean rearBumper;
    private Boolean chassis;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
