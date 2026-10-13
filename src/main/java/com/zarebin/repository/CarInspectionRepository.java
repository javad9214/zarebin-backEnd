package com.zarebin.repository;

import com.zarebin.entity.CarInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarInspectionRepository extends JpaRepository<CarInspection, Long> {
    List<CarInspection> findAllByOrderByInspectionDateDesc();

    Optional<CarInspection> findByPublicId(UUID publicId);

    List<CarInspection> findByPublicIdIsNull();
}
