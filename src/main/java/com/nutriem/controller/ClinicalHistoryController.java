package com.nutriem.controller;

import com.nutriem.dto.request.ClinicalHistoryRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.ClinicalHistoryResponse;
import com.nutriem.service.ClinicalHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients/{patientId}/clinical-history")
public class ClinicalHistoryController {

    private final ClinicalHistoryService service;

    public ClinicalHistoryController(ClinicalHistoryService service) {
        this.service = service;
    }

    // GET /api/patients/{id}/clinical-history
    @GetMapping
    public ResponseEntity<ApiResponse<ClinicalHistoryResponse>> get(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.ok(service.get(patientId)));
    }

    // PUT /api/patients/{id}/clinical-history  (upsert — create or update)
    @PutMapping
    public ResponseEntity<ApiResponse<ClinicalHistoryResponse>> upsert(
            @PathVariable Long patientId,
            @RequestBody ClinicalHistoryRequest req) {
        return ResponseEntity.ok(ApiResponse.ok(service.upsert(patientId, req)));
    }
}
