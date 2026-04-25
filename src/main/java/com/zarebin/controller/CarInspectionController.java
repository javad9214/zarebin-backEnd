package com.zarebin.controller;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.service.CarInspectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(created, HttpStatus.CREATED);
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
        List<CarInspectionDto> inspections = service.getAll();
        return ResponseEntity.ok(inspections);
    }

    @GetMapping("/chassis/{chassisNumber}")
    public ResponseEntity<List<CarInspectionDto>> getByChassisNumber(@PathVariable String chassisNumber) {
        log.info("GET /api/inspections/chassis/{} - Fetching by chassis number", chassisNumber);
        List<CarInspectionDto> inspections = service.getByChassisNumber(chassisNumber);
        return ResponseEntity.ok(inspections);
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<List<CarInspectionDto>> getByPlateNumber(@PathVariable String plateNumber) {
        log.info("GET /api/inspections/plate/{} - Fetching by plate number", plateNumber);
        List<CarInspectionDto> inspections = service.getByPlateNumber(plateNumber);
        return ResponseEntity.ok(inspections);
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
