package com.nutriem.controller;

import com.nutriem.dto.response.*;
import com.nutriem.model.Patient;
import com.nutriem.repository.*;
import com.nutriem.security.PatientUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portal")
@PreAuthorize("hasRole('PATIENT')")
@Transactional(readOnly = true)
public class PortalController {

    private final PatientRepository       patientRepository;
    private final DietPlanRepository      dietPlanRepository;
    private final AnthropometryRepository anthropometryRepository;
    private final AppointmentRepository   appointmentRepository;
    private final ClinicalHistoryRepository clinicalHistoryRepository;

    public PortalController(PatientRepository patientRepository,
                             DietPlanRepository dietPlanRepository,
                             AnthropometryRepository anthropometryRepository,
                             AppointmentRepository appointmentRepository,
                             ClinicalHistoryRepository clinicalHistoryRepository) {
        this.patientRepository         = patientRepository;
        this.dietPlanRepository        = dietPlanRepository;
        this.anthropometryRepository   = anthropometryRepository;
        this.appointmentRepository     = appointmentRepository;
        this.clinicalHistoryRepository = clinicalHistoryRepository;
    }

    private Long currentPatientId() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        return PatientUserDetailsService.extractPatientId(subject);
    }

    private Patient currentPatient() {
        return patientRepository.findById(currentPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    // GET /api/portal/me
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<PatientResponse>> me() {
        return ResponseEntity.ok(ApiResponse.ok(PatientResponse.from(currentPatient())));
    }

    // GET /api/portal/diet-plans
    // Two-pass load to avoid Hibernate MultipleBagFetchException:
    //   Pass 1: load DietPlan + Meals (one bag)
    //   Pass 2: load DietPlan + Meals + MealFoods + Food (second bag, Hibernate merges by identity)
    @GetMapping("/diet-plans")
    public ResponseEntity<ApiResponse<List<DietPlanResponse>>> dietPlans() {
        Long patientId = currentPatientId();
        List<DietPlanResponse> plans = dietPlanRepository
                .findAllByPatientIdWithMeals(patientId)
                .stream()
                .map(DietPlanResponse::withMeals)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(plans));
    }

    // GET /api/portal/diet-plans/{id}
    @GetMapping("/diet-plans/{id}")
    public ResponseEntity<ApiResponse<DietPlanResponse>> dietPlan(@PathVariable Long id) {
        Long patientId = currentPatientId();
        return dietPlanRepository.findById(id)
                .filter(p -> p.getPatient().getId().equals(patientId))
                .map(p -> ResponseEntity.ok(ApiResponse.ok(DietPlanResponse.withMeals(p))))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/portal/appointments
    @GetMapping("/appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> appointments() {
        Long patientId = currentPatientId();
        List<AppointmentResponse> appts = appointmentRepository
                .findByPatientAndNutritionist(patientId, currentPatient().getNutrologist().getId())
                .stream()
                .map(AppointmentResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(appts));
    }

    // GET /api/portal/anthropometry
    @GetMapping("/anthropometry")
    public ResponseEntity<ApiResponse<List<AnthropometryResponse>>> anthropometry() {
        Long patientId = currentPatientId();
        List<AnthropometryResponse> records = anthropometryRepository
                .findAllByPatientId(patientId)
                .stream()
                .map(AnthropometryResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(records));
    }
}
