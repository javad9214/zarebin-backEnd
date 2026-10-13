package com.zarebin.service;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.entity.CarInspection;
import com.zarebin.exception.ResourceNotFoundException;
import com.zarebin.repository.CarInspectionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarInspectionService {

    private final CarInspectionRepository repository;

    @Transactional
    public CarInspectionDto create(CarInspectionDto dto) {
        CarInspection saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    public CarInspectionDto getById(Long id) {
        CarInspection e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found: " + id));
        return toDto(e);
    }

    /**
     * Customer-facing lookup by the unguessable share key used in web report links.
     */
    public CarInspectionDto getByPublicId(UUID publicId) {
        CarInspection e = repository.findByPublicId(publicId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found: " + publicId));
        return toDto(e);
    }

    public List<CarInspectionDto> getAll() {
        return repository.findAllByOrderByInspectionDateDesc().stream().map(this::toDto).toList();
    }

    /**
     * Assign a publicId to inspections created before the field existed, so
     * every record is shareable via a web link. Runs once at startup.
     */
    @Transactional
    public void backfillPublicIds() {
        List<CarInspection> missing = repository.findByPublicIdIsNull();
        for (CarInspection e : missing) {
            e.setPublicId(UUID.randomUUID());
        }
        repository.saveAll(missing);
        if (!missing.isEmpty()) {
            log.info("Backfilled publicId for {} inspection(s)", missing.size());
        }
    }

    @Transactional
    public CarInspectionDto update(Long id, CarInspectionDto dto) {
        CarInspection e = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found: " + id));

        e.setFullName(dto.getFullName());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setCarName(dto.getCarName());
        e.setCarModel(dto.getCarModel());
        e.setChassisNumber(dto.getChassisNumber());
        e.setPlateNumber(dto.getPlateNumber());
        e.setInspectionDate(dto.getInspectionDate());
        e.setExpertName(dto.getExpertName());
        e.setExpertPhoneNumber(dto.getExpertPhoneNumber());
        e.setExpertDescription(dto.getExpertDescription());

        e.setRoof(dto.getRoof());
        e.setHood(dto.getHood());
        e.setChassis(dto.getChassis());
        e.setFrontLeftFender(dto.getFrontLeftFender());
        e.setFrontRightFender(dto.getFrontRightFender());
        e.setRearLeftFender(dto.getRearLeftFender());
        e.setRearRightFender(dto.getRearRightFender());
        e.setFrontLeftDoor(dto.getFrontLeftDoor());
        e.setFrontRightDoor(dto.getFrontRightDoor());
        e.setRearLeftDoor(dto.getRearLeftDoor());
        e.setRearRightDoor(dto.getRearRightDoor());
        e.setTrunk(dto.getTrunk());

        return toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Not found: " + id);
        repository.deleteById(id);
    }


    public CarInspection getEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found with id: " + id));
    }


    private CarInspection toEntity(CarInspectionDto d) {
        CarInspection e = new CarInspection();

        e.setId(d.getId());
        e.setFullName(d.getFullName());
        e.setPhoneNumber(d.getPhoneNumber());
        e.setCarName(d.getCarName());
        e.setCarModel(d.getCarModel());
        e.setChassisNumber(d.getChassisNumber());
        e.setPlateNumber(d.getPlateNumber());
        e.setInspectionDate(d.getInspectionDate());
        e.setExpertName(d.getExpertName());
        e.setExpertPhoneNumber(d.getExpertPhoneNumber());
        e.setExpertDescription(d.getExpertDescription());

        e.setRoof(d.getRoof());
        e.setHood(d.getHood());
        e.setChassis(d.getChassis());
        e.setFrontLeftFender(d.getFrontLeftFender());
        e.setFrontRightFender(d.getFrontRightFender());
        e.setRearLeftFender(d.getRearLeftFender());
        e.setRearRightFender(d.getRearRightFender());
        e.setFrontLeftDoor(d.getFrontLeftDoor());
        e.setFrontRightDoor(d.getFrontRightDoor());
        e.setRearLeftDoor(d.getRearLeftDoor());
        e.setRearRightDoor(d.getRearRightDoor());
        e.setTrunk(d.getTrunk());

        return e;
    }

    private CarInspectionDto toDto(CarInspection e) {
        CarInspectionDto d = new CarInspectionDto();

        d.setId(e.getId());
        d.setPublicId(e.getPublicId());
        d.setFullName(e.getFullName());
        d.setPhoneNumber(e.getPhoneNumber());
        d.setCarName(e.getCarName());
        d.setCarModel(e.getCarModel());
        d.setChassisNumber(e.getChassisNumber());
        d.setPlateNumber(e.getPlateNumber());
        d.setInspectionDate(e.getInspectionDate());
        d.setExpertName(e.getExpertName());
        d.setExpertPhoneNumber(e.getExpertPhoneNumber());
        d.setExpertDescription(e.getExpertDescription());

        d.setRoof(e.getRoof());
        d.setHood(e.getHood());
        d.setChassis(e.getChassis());
        d.setFrontLeftFender(e.getFrontLeftFender());
        d.setFrontRightFender(e.getFrontRightFender());
        d.setRearLeftFender(e.getRearLeftFender());
        d.setRearRightFender(e.getRearRightFender());
        d.setFrontLeftDoor(e.getFrontLeftDoor());
        d.setFrontRightDoor(e.getFrontRightDoor());
        d.setRearLeftDoor(e.getRearLeftDoor());
        d.setRearRightDoor(e.getRearRightDoor());
        d.setTrunk(e.getTrunk());

        d.setCreatedAt(e.getCreatedAt());
        d.setUpdatedAt(e.getUpdatedAt());

        return d;
    }
}
