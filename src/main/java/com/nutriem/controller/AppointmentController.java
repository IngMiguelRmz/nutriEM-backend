package com.nutriem.controller;

import com.nutriem.dto.request.AppointmentRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.AppointmentResponse;
import com.nutriem.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // POST /api/patients/{id}/appointments
    @PostMapping("/patients/{patientId}/appointments")
    public ResponseEntity<ApiResponse<AppointmentResponse>> create(
            @PathVariable Long patientId,
            @RequestBody AppointmentRequest req) {
        return ResponseEntity.ok(ApiResponse.ok(appointmentService.create(patientId, req)));
    }

    // GET /api/patients/{id}/appointments?from=2024-01-01&to=2024-01-31
    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getForPatient(
            @PathVariable Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.ok(appointmentService.getForPatient(patientId, from, to)));
    }

    // PUT /api/appointments/{id}
    @PutMapping("/appointments/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> update(
            @PathVariable Long id,
            @RequestBody AppointmentRequest req) {
        return ResponseEntity.ok(ApiResponse.ok(appointmentService.update(id, req)));
    }

    // GET /api/appointments?from=2024-01-01&to=2024-01-31  (all patients, for global calendar)
    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAll(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(ApiResponse.ok(appointmentService.getAllForNutritionist(from, to)));
    }

    // GET /api/appointments/today
    @GetMapping("/appointments/today")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getToday() {
        return ResponseEntity.ok(ApiResponse.ok(appointmentService.getToday()));
    }

    // DELETE /api/appointments/{id}
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
