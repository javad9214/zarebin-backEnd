package com.zarebin.controller;

import com.zarebin.dto.CarInspectionDto;
import com.zarebin.dto.PdfRequest;
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


    @GetMapping("/{id}/pdf")
    public ResponseEntity<String> generatePdfViaPython(@PathVariable Long id) {
        CarInspection inspection = service.getEntityById(id);

        PdfRequest req = new PdfRequest();
        req.setInspectionId(inspection.getId());
        req.setFullName(inspection.getFullName());
        req.setPhoneNumber(inspection.getPhoneNumber());
        req.setCarName(inspection.getCarName());
        req.setCarModel(inspection.getCarModel());
        req.setPlateNumber(inspection.getPlateNumber());
        req.setChassisNumber(inspection.getChassisNumber());
        req.setExpertDescription(inspection.getExpertDescription());
        req.setInspectionDate(DateUtil.gregorianToShamsiLocalDate(inspection.getInspectionDate().toLocalDate()).toString());
        log.info(DateUtil.gregorianToShamsiLocalDate(inspection.getInspectionDate().toLocalDate()).toString());
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

        String result = carInspectionPdfService.sendToPython(req);
        return ResponseEntity.ok(result);
    }


}
