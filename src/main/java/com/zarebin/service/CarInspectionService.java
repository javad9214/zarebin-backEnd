package com.zarebin.service;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.entity.CarInspection;
import com.zarebin.exception.ResourceNotFoundException;
import com.zarebin.repository.CarInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarInspectionService {

    private final CarInspectionRepository repository;

    @Transactional
    public CarInspectionDto create(CarInspectionDto dto) {
        CarInspection entity = toEntity(dto);
        CarInspection saved = repository.save(entity);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public CarInspectionDto getById(Long id) {
        CarInspection entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspection not found with id: " + id));
        return toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CarInspectionDto> getByCarId(String carId) {
        return repository.findByCarId(carId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarInspectionDto update(Long id, CarInspectionDto dto) {
        CarInspection existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inspection not found with id: " + id));

        existing.setUserId(dto.getUserId());
        existing.setCarId(dto.getCarId());
        existing.setInspectionDate(dto.getInspectionDate());
        existing.setMileage(dto.getMileage());
        existing.setNotes(dto.getNotes());

        CarInspection updated = repository.save(existing);
        return toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Inspection not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private CarInspection toEntity(CarInspectionDto dto) {
        CarInspection entity = new CarInspection();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setCarId(dto.getCarId());
        entity.setInspectionDate(dto.getInspectionDate());
        entity.setMileage(dto.getMileage());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    private CarInspectionDto toDto(CarInspection entity) {
        return new CarInspectionDto(
                entity.getId(),
                entity.getUserId(),
                entity.getCarId(),
                entity.getInspectionDate(),
                entity.getMileage(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
