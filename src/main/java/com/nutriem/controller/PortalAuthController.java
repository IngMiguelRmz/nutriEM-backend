package com.nutriem.controller;

import com.nutriem.dto.response.ApiResponse;
import com.nutriem.model.Patient;
import com.nutriem.repository.PatientRepository;
import com.nutriem.security.JwtUtils;
import com.nutriem.security.PatientUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/portal/auth")
public class PortalAuthController {

    private final PatientRepository patientRepository;
    private final PasswordEncoder   passwordEncoder;
    private final JwtUtils          jwtUtils;

    public PortalAuthController(PatientRepository patientRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtils jwtUtils) {
        this.patientRepository = patientRepository;
        this.passwordEncoder   = passwordEncoder;
        this.jwtUtils          = jwtUtils;
    }

    // POST /api/portal/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String,Object>>> login(@RequestBody Map<String,String> req) {
        String email    = req.get("email");
        String password = req.get("password");

        if (email == null || password == null)
            return ResponseEntity.badRequest().body(ApiResponse.error("Email and password required"));

        Optional<Patient> opt = patientRepository.findByEmail(email);
        if (opt.isEmpty())
            return ResponseEntity.status(401).body(ApiResponse.error("Invalid credentials"));

        Patient patient = opt.get();
        if (patient.getPortalPassword() == null)
            return ResponseEntity.status(401).body(ApiResponse.error("Portal access not enabled for this account"));

        if (!passwordEncoder.matches(password, patient.getPortalPassword()))
            return ResponseEntity.status(401).body(ApiResponse.error("Invalid credentials"));

        String token = jwtUtils.generateToken(PatientUserDetailsService.toSubject(patient.getId()));

        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "token",     token,
            "patientId", patient.getId(),
            "name",      patient.getFullName(),
            "email",     patient.getEmail()
        )));
    }
}
