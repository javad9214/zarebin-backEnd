package com.zarebin.service;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.entity.CarInspection;
import com.zarebin.exception.ResourceNotFoundException;
import com.zarebin.repository.CarInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInspectionService {

    private final CarInspectionRepository repository;

    @Transactional
    public CarInspectionDto create(CarInspectionDto dto) {
        log.info("Creating new car inspection for: {}", dto.getFullName());
        CarInspection entity = toEntity(dto);
        CarInspection saved = repository.save(entity);
        log.info("Car inspection created with ID: {}", saved.getId());
        return toDto(saved);
    }

    public CarInspectionDto getById(Long id) {
        log.info("Fetching car inspection with ID: {}", id);
        CarInspection entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car inspection not found with ID: " + id));
        return toDto(entity);
    }

    public List<CarInspectionDto> getAll() {
        log.info("Fetching all car inspections");
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CarInspectionDto> getByChassisNumber(String chassisNumber) {
        log.info("Fetching car inspections by chassis number: {}", chassisNumber);
        return repository.findByChassisNumber(chassisNumber).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CarInspectionDto> getByPlateNumber(String plateNumber) {
        log.info("Fetching car inspections by plate number: {}", plateNumber);
        return repository.findByPlateNumber(plateNumber).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CarInspectionDto update(Long id, CarInspectionDto dto) {
        log.info("Updating car inspection with ID: {}", id);
        CarInspection existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car inspection not found with ID: " + id));

        existing.setFullName(dto.getFullName());
        existing.setPhoneNumber(dto.getPhoneNumber());
        existing.setCarName(dto.getCarName());
        existing.setCarModel(dto.getCarModel());
        existing.setChassisNumber(dto.getChassisNumber());
        existing.setPlateNumber(dto.getPlateNumber());
        existing.setExpertDescription(dto.getExpertDescription());

        existing.setFrontBumper(dto.getFrontBumper());
        existing.setHood(dto.getHood());
        existing.setFrontRightFender(dto.getFrontRightFender());
        existing.setFrontRightDoor(dto.getFrontRightDoor());
        existing.setRearRightDoor(dto.getRearRightDoor());
        existing.setRearRightFender(dto.getRearRightFender());
        existing.setTrunkLid(dto.getTrunkLid());
        existing.setRearLeftFender(dto.getRearLeftFender());
        existing.setRearLeftDoor(dto.getRearLeftDoor());
        existing.setFrontLeftDoor(dto.getFrontLeftDoor());
        existing.setFrontLeftFender(dto.getFrontLeftFender());
        existing.setRoof(dto.getRoof());
        existing.setRearBumper(dto.getRearBumper());

        CarInspection updated = repository.save(existing);
        log.info("Car inspection updated successfully");
        return toDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting car inspection with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Car inspection not found with ID: " + id);
        }
        repository.deleteById(id);
        log.info("Car inspection deleted successfully");
    }

    private CarInspection toEntity(CarInspectionDto dto) {
        CarInspection entity = new CarInspection();
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setCarName(dto.getCarName());
        entity.setCarModel(dto.getCarModel());
        entity.setChassisNumber(dto.getChassisNumber());
        entity.setPlateNumber(dto.getPlateNumber());
        entity.setExpertDescription(dto.getExpertDescription());

        entity.setFrontBumper(dto.getFrontBumper());
        entity.setHood(dto.getHood());
        entity.setFrontRightFender(dto.getFrontRightFender());
        entity.setFrontRightDoor(dto.getFrontRightDoor());
        entity.setRearRightDoor(dto.getRearRightDoor());
        entity.setRearRightFender(dto.getRearRightFender());
        entity.setTrunkLid(dto.getTrunkLid());
        entity.setRearLeftFender(dto.getRearLeftFender());
        entity.setRearLeftDoor(dto.getRearLeftDoor());
        entity.setFrontLeftDoor(dto.getFrontLeftDoor());
        entity.setFrontLeftFender(dto.getFrontLeftFender());
        entity.setRoof(dto.getRoof());
        entity.setRearBumper(dto.getRearBumper());

        return entity;
    }

    private CarInspectionDto toDto(CarInspection entity) {
        CarInspectionDto dto = new CarInspectionDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setCarName(entity.getCarName());
        dto.setCarModel(entity.getCarModel());
        dto.setChassisNumber(entity.getChassisNumber());
        dto.setPlateNumber(entity.getPlateNumber());
        dto.setExpertDescription(entity.getExpertDescription());

        dto.setFrontBumper(entity.getFrontBumper());
        dto.setHood(entity.getHood());
        dto.setFrontRightFender(entity.getFrontRightFender());
        dto.setFrontRightDoor(entity.getFrontRightDoor());
        dto.setRearRightDoor(entity.getRearRightDoor());
        dto.setRearRightFender(entity.getRearRightFender());
        dto.setTrunkLid(entity.getTrunkLid());
        dto.setRearLeftFender(entity.getRearLeftFender());
        dto.setRearLeftDoor(entity.getRearLeftDoor());
        dto.setFrontLeftDoor(entity.getFrontLeftDoor());
        dto.setFrontLeftFender(entity.getFrontLeftFender());
        dto.setRoof(entity.getRoof());
        dto.setRearBumper(entity.getRearBumper());

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
