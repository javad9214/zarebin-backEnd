package com.zarebin.service;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.entity.CarInspection;
import com.zarebin.exception.ResourceNotFoundException;
import com.zarebin.repository.CarInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInspectionService {

    private final CarInspectionRepository repository;

    @Transactional
    public CarInspectionDto create(CarInspectionDto dto) {
        log.info("Creating new car inspection for userId: {}, carId: {}", dto.getUserId(), dto.getCarId());
        CarInspection entity = toEntity(dto);
        CarInspection saved = repository.save(entity);
        log.info("Car inspection created successfully with id: {}", saved.getId());
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public CarInspectionDto getById(Long id) {
        log.debug("Fetching car inspection with id: {}", id);
        CarInspection entity = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car inspection not found with id: {}", id);
                    return new ResourceNotFoundException("Car inspection not found with id: " + id);
                });
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getAll() {
        log.debug("Fetching all car inspections");
        List<CarInspectionDto> inspections = repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Found {} car inspections", inspections.size());
        return inspections;
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getByUserId(String userId) {
        log.debug("Fetching car inspections for userId: {}", userId);
        List<CarInspectionDto> inspections = repository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Found {} car inspections for userId: {}", inspections.size(), userId);
        return inspections;
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getByCarId(String carId) {
        log.debug("Fetching car inspections for carId: {}", carId);
        List<CarInspectionDto> inspections = repository.findByCarId(carId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        log.info("Found {} car inspections for carId: {}", inspections.size(), carId);
        return inspections;
    }

    @Transactional
    public CarInspectionDto update(Long id, CarInspectionDto dto) {
        log.info("Updating car inspection with id: {}", id);
        CarInspection entity = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Car inspection not found with id: {}", id);
                    return new ResourceNotFoundException("Car inspection not found with id: " + id);
                });

        entity.setUserId(dto.getUserId());
        entity.setCarId(dto.getCarId());
        entity.setInspectionDate(dto.getInspectionDate());
        entity.setMileage(dto.getMileage());
        entity.setNotes(dto.getNotes());

        CarInspection updated = repository.save(entity);
        log.info("Car inspection updated successfully with id: {}", id);
        return toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting car inspection with id: {}", id);
        if (!repository.existsById(id)) {
            log.error("Car inspection not found with id: {}", id);
            throw new ResourceNotFoundException("Car inspection not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Car inspection deleted successfully with id: {}", id);
    }

    private CarInspection toEntity(CarInspectionDto dto) {
        CarInspection entity = new CarInspection();
        entity.setUserId(dto.getUserId());
        entity.setCarId(dto.getCarId());
        entity.setInspectionDate(dto.getInspectionDate());
        entity.setMileage(dto.getMileage());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    private CarInspectionDto toDto(CarInspection entity) {
        CarInspectionDto dto = new CarInspectionDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setCarId(entity.getCarId());
        dto.setInspectionDate(entity.getInspectionDate());
        dto.setMileage(entity.getMileage());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
