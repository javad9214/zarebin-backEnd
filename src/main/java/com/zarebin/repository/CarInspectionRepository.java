package com.zarebin.repository;

import com.zarebin.entity.CarInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarInspectionRepository extends JpaRepository<CarInspection, Long> {

    List<CarInspection> findByUserId(String userId);

    List<CarInspection> findByCarId(String carId);

    List<CarInspection> findByUserIdAndCarId(String userId, String carId);
}
