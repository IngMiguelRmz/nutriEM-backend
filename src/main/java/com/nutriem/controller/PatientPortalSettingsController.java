package com.nutriem.controller;

import com.nutriem.dto.response.ApiResponse;
import com.nutriem.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/patients/{patientId}/portal")
public class PatientPortalSettingsController {

    private final PatientService patientService;

    public PatientPortalSettingsController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET /api/patients/{id}/portal/status
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String,Object>>> status(@PathVariable Long patientId) {
        boolean enabled = patientService.isPortalEnabled(patientId);
        return ResponseEntity.ok(ApiResponse.ok(Map.of("enabled", enabled)));
    }

    // POST /api/patients/{id}/portal/enable  { "password": "..." }
    @PostMapping("/enable")
    public ResponseEntity<ApiResponse<Map<String,String>>> enable(
            @PathVariable Long patientId, @RequestBody Map<String,String> req) {
        String pwd = req.get("password");
        if (pwd == null || pwd.length() < 6)
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Password must be at least 6 characters"));
        return ResponseEntity.ok(ApiResponse.ok(patientService.setPortalPassword(patientId, pwd)));
    }

    // DELETE /api/patients/{id}/portal/disable
    @DeleteMapping("/disable")
    public ResponseEntity<ApiResponse<Map<String,String>>> disable(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.ok(patientService.disablePortal(patientId)));
    }
}
