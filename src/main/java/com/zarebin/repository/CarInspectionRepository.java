package com.zarebin.repository;

import com.zarebin.entity.CarInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarInspectionRepository extends JpaRepository<CarInspection, Long> {
    List<CarInspection> findByChassisNumber(String chassisNumber);
    List<CarInspection> findByPlateNumber(String plateNumber);
    List<CarInspection> findAllByOrderByInspectionDateDesc();
    List<CarInspection> findByPhoneNumber(String phoneNumber);
    List<CarInspection> findByFullNameContainingIgnoreCase(String fullName);
    List<CarInspection> findByInspectionDateBetween(LocalDateTime start, LocalDateTime end);

}
