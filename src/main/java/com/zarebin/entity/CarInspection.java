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

    @Column(columnDefinition = "TEXT")
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
