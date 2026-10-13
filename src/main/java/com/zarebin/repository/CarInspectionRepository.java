package com.zarebin.repository;

import com.zarebin.entity.CarInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarInspectionRepository extends JpaRepository<CarInspection, Long> {
    List<CarInspection> findAllByOrderByInspectionDateDesc();

    Optional<CarInspection> findByPublicId(UUID publicId);

    /**
     * One-time migration for rows created before publicId existed. Native SQL on
     * purpose: the entity maps public_id as updatable=false (protects the share
     * key from being changed), which also excludes it from JPA UPDATEs — so a
     * load/modify/save backfill would silently do nothing.
     */
    @Modifying
    @Query(value = "UPDATE car_inspections SET public_id = gen_random_uuid() WHERE public_id IS NULL", nativeQuery = true)
    int backfillPublicIds();
}
