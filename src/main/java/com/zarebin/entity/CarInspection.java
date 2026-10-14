package com.zarebin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "car_inspections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unguessable share key backing the customer-facing web link
     * (/report/{publicId}). Deliberately separate from the internal
     * auto-increment id the expert app uses, so sequential ids are never
     * exposed as capability URLs. Nullable so the ddl-auto=update migration
     * on existing rows succeeds; rows created after this field are always
     * assigned one (see {@link #onCreate()}) and old rows are backfilled at startup.
     */
    @Column(unique = true, updatable = false)
    private UUID publicId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String carName;

    @Column(nullable = false)
    private String carModel;

    /** Optional since Oct 2026; null when the expert leaves it blank. */
    private String chassisNumber;

    @Embedded
    private PlateNumber plateNumber;

    @Column(nullable = false)
    private LocalDateTime inspectionDate;

    private String expertName;

    private String expertPhoneNumber;

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

    @PrePersist
    void onCreate() {
        if (publicId == null) {
            publicId = UUID.randomUUID();
        }
    }
}
