package com.nutriem.controller;

import com.nutriem.dto.request.MeasurementRequest;
import com.nutriem.dto.request.PatientRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.MeasurementResponse;
import com.nutriem.dto.response.PageResponse;
import com.nutriem.dto.response.PatientResponse;
import com.nutriem.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // POST /api/patients
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientRequest req) {
        PatientResponse response = patientService.createPatient(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Patient created successfully", response));
    }

    // GET /api/patients?page=0&size=10&search=maria
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PatientResponse>>> getPatients(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "")   String search) {
        PageResponse<PatientResponse> response = patientService.getPatients(page, size, search);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // GET /api/patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(patientService.getPatientById(id)));
    }

    // PUT /api/patients/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest req) {
        return ResponseEntity.ok(
                ApiResponse.ok("Patient updated successfully", patientService.updatePatient(id, req)));
    }

    // DELETE /api/patients/{id}  (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.ok("Patient deactivated", null));
    }

    // POST /api/patients/{id}/measurements
    @PostMapping("/{id}/measurements")
    public ResponseEntity<ApiResponse<MeasurementResponse>> addMeasurement(
            @PathVariable Long id,
            @Valid @RequestBody MeasurementRequest req) {
        MeasurementResponse response = patientService.addMeasurement(id, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Measurement recorded", response));
    }

    // GET /api/patients/{id}/measurements
    @GetMapping("/{id}/measurements")
    public ResponseEntity<ApiResponse<List<MeasurementResponse>>> getMeasurements(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(patientService.getMeasurements(id)));
    }
}
