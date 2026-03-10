package com.nutriem.controller;

import com.nutriem.dto.request.AnthropometryRequest;
import com.nutriem.dto.response.AnthropometryResponse;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.service.AnthropometryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnthropometryController {

    private final AnthropometryService anthropometryService;

    public AnthropometryController(AnthropometryService anthropometryService) {
        this.anthropometryService = anthropometryService;
    }

    // POST /api/patients/{patientId}/anthropometry
    @PostMapping("/patients/{patientId}/anthropometry")
    public ResponseEntity<ApiResponse<AnthropometryResponse>> create(
            @PathVariable Long patientId,
            @RequestBody AnthropometryRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(anthropometryService.create(patientId, request)));
    }

    // GET /api/patients/{patientId}/anthropometry
    @GetMapping("/patients/{patientId}/anthropometry")
    public ResponseEntity<ApiResponse<List<AnthropometryResponse>>> getAll(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.ok(anthropometryService.getAll(patientId)));
    }

    // GET /api/anthropometry/{id}
    @GetMapping("/anthropometry/{id}")
    public ResponseEntity<ApiResponse<AnthropometryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(anthropometryService.getById(id)));
    }

    // PUT /api/anthropometry/{id}
    @PutMapping("/anthropometry/{id}")
    public ResponseEntity<ApiResponse<AnthropometryResponse>> update(
            @PathVariable Long id,
            @RequestBody AnthropometryRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(anthropometryService.update(id, request)));
    }

    // DELETE /api/anthropometry/{id}
    @DeleteMapping("/anthropometry/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        anthropometryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
