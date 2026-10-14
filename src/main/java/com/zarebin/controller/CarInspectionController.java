package com.zarebin.controller;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.dto.PdfRequest;
import com.zarebin.dto.PdfResponse;
import com.zarebin.entity.CarInspection;
import com.zarebin.service.CarInspectionPdfService;
import com.zarebin.service.CarInspectionPdfServiceLocally;
import com.zarebin.service.CarInspectionService;
import com.zarebin.utility.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inspections")
@RequiredArgsConstructor
@Slf4j
public class CarInspectionController {

    private final CarInspectionService service;
    private final CarInspectionPdfServiceLocally pdfServiceLocally;
    private final CarInspectionPdfService carInspectionPdfService;

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

    /**
     * Customer-facing endpoint backing the web report link (/report/{publicId});
     * the UUID share key is the only credential required.
     */
    @GetMapping("/public/{publicId}")
    public ResponseEntity<CarInspectionDto> getByPublicId(@PathVariable UUID publicId) {
        log.info("GET /api/inspections/public/{} - Fetching inspection by public id", publicId);
        CarInspectionDto dto = service.getByPublicId(publicId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CarInspectionDto>> getAll() {
        log.info("GET /api/inspections - Fetching all inspections");
        List<CarInspectionDto> inspections = service.getAll();
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


    @GetMapping("/{id}/pdf")
    public ResponseEntity<PdfResponse> generatePdfViaPython(@PathVariable Long id) {
        CarInspection inspection = service.getEntityById(id);

        PdfRequest req = new PdfRequest();
        req.setInspectionId(inspection.getId());
        req.setFullName(inspection.getFullName());
        req.setPhoneNumber(inspection.getPhoneNumber());
        req.setCarName(inspection.getCarName());
        req.setCarModel(inspection.getCarModel());
        req.setPlateNumber(inspection.getPlateNumber() != null
                ? inspection.getPlateNumber().toString() : null);
        req.setChassisNumber(inspection.getChassisNumber());
        req.setExpertName(inspection.getExpertName());
        req.setExpertPhoneNumber(inspection.getExpertPhoneNumber());
        req.setExpertDescription(inspection.getExpertDescription());
        req.setInspectionDate(DateUtil.gregorianToShamsiString(inspection.getInspectionDate().toLocalDate()));
        req.setInspectionTime(DateUtil.getTime(inspection.getInspectionDate()));
        req.setRoof(inspection.getRoof());
        req.setChassis(inspection.getChassis());
        req.setFrontLeftFender(inspection.getFrontLeftFender());
        req.setFrontRightFender(inspection.getFrontRightFender());
        req.setRearLeftFender(inspection.getRearLeftFender());
        req.setRearRightFender(inspection.getRearRightFender());
        req.setHood(inspection.getHood());
        req.setTrunk(inspection.getTrunk());
        req.setFrontLeftDoor(inspection.getFrontLeftDoor());
        req.setFrontRightDoor(inspection.getFrontRightDoor());
        req.setRearLeftDoor(inspection.getRearLeftDoor());
        req.setRearRightDoor(inspection.getRearRightDoor());

        PdfResponse result = carInspectionPdfService.sendToPython(req);
        return ResponseEntity.ok(result);
    }


}
