package com.zarebin.controller;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.service.CarInspectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
@Slf4j
public class CarInspectionController {

    private final CarInspectionService service;

    @PostMapping
    public ResponseEntity<CarInspectionDto> create(@Valid @RequestBody CarInspectionDto dto) {
        log.info("POST /api/inspections - Creating new inspection");
        CarInspectionDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarInspectionDto> getById(@PathVariable Long id) {
        log.info("GET /api/inspections/{} - Fetching inspection", id);
        CarInspectionDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CarInspectionDto>> getAll() {
        log.info("GET /api/inspections - Fetching all inspections");
        List<CarInspectionDto> dtos = service.getAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CarInspectionDto>> getByUserId(@PathVariable String userId) {
        log.info("GET /api/inspections/user/{} - Fetching inspections by userId", userId);
        List<CarInspectionDto> dtos = service.getByUserId(userId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<CarInspectionDto>> getByCarId(@PathVariable String carId) {
        log.info("GET /api/inspections/car/{} - Fetching inspections by carId", carId);
        List<CarInspectionDto> dtos = service.getByCarId(carId);
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarInspectionDto> update(@PathVariable Long id, @Valid @RequestBody CarInspectionDto dto) {
        log.info("PUT /api/inspections/{} - Updating inspection", id);
        CarInspectionDto updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/inspections/{} - Deleting inspection", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
