package com.nutriem.service;

import com.nutriem.dto.request.MeasurementRequest;
import com.nutriem.dto.request.PatientRequest;
import com.nutriem.dto.response.MeasurementResponse;
import com.nutriem.dto.response.PageResponse;
import com.nutriem.dto.response.PatientResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.Patient;
import com.nutriem.model.PatientMeasurement;
import com.nutriem.model.User;
import com.nutriem.repository.PatientMeasurementRepository;
import com.nutriem.repository.PatientRepository;
import com.nutriem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository            patientRepository;
    private final PatientMeasurementRepository measurementRepository;
    private final UserRepository               userRepository;
    private final PasswordEncoder              passwordEncoder;

    public PatientService(PatientRepository patientRepository,
                          PatientMeasurementRepository measurementRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.patientRepository    = patientRepository;
        this.measurementRepository = measurementRepository;
        this.userRepository        = userRepository;
        this.passwordEncoder       = passwordEncoder;
    }

    // ── Get current logged-in user ────────────────────────────
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    // ── CREATE ────────────────────────────────────────────────
    @Transactional
    public PatientResponse createPatient(PatientRequest req) {
        User nutriologist = getCurrentUser();

        Patient patient = new Patient();
        patient.setFirstName(req.getFirstName());
        patient.setLastName(req.getLastName());
        patient.setEmail(req.getEmail());
        patient.setPhoneNumber(req.getPhoneNumber());
        patient.setDateOfBirth(req.getDateOfBirth());
        patient.setGender(parseEnum(Patient.Gender.class, req.getGender()));
        patient.setWeightKg(req.getWeightKg());
        patient.setHeightCm(req.getHeightCm());
        patient.setMedicalConditions(req.getMedicalConditions());
        patient.setAllergies(req.getAllergies());
        patient.setFoodPreferences(req.getFoodPreferences());
        patient.setFoodRestrictions(req.getFoodRestrictions());
        patient.setPrimaryGoal(parseEnum(Patient.Goal.class, req.getPrimaryGoal()));
        patient.setActivityLevel(parseEnum(Patient.ActivityLevel.class, req.getActivityLevel()));
        patient.setNotes(req.getNotes());
        patient.setNutrologist(nutriologist);

        Patient saved = patientRepository.save(patient);
        log.info("Patient created: {} by {}", saved.getFullName(), nutriologist.getEmail());

        // Auto-create first measurement from registration data
        if (req.getWeightKg() != null || req.getHeightCm() != null) {
            PatientMeasurement m = new PatientMeasurement();
            m.setPatient(saved);
            m.setWeightKg(req.getWeightKg());
            m.setHeightCm(req.getHeightCm());
            m.setMeasuredAt(java.time.LocalDate.now());
            m.setNotes("Initial measurement from patient registration");
            measurementRepository.save(m);
        }

        return PatientResponse.from(saved);
    }

    // ── GET ALL (paginated + search) ──────────────────────────
    @Transactional(readOnly = true)
    public PageResponse<PatientResponse> getPatients(int page, int size, String search) {
        User nutriologist = getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());

        Page<Patient> result;
        if (StringUtils.hasText(search)) {
            result = patientRepository.searchByNutriologist(nutriologist.getId(), search, pageable);
        } else {
            result = patientRepository.findAllByNutritionistId(nutriologist.getId(), pageable);
        }

        Page<PatientResponse> mapped = result.map(PatientResponse::from);
        return PageResponse.from(mapped);
    }

    // ── GET BY ID ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        User nutriologist = getCurrentUser();
        Patient patient = patientRepository
                .findByIdAndNutritionistId(id, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        return PatientResponse.summary(patient);
    }

    // ── UPDATE ────────────────────────────────────────────────
    @Transactional
    public PatientResponse updatePatient(Long id, PatientRequest req) {
        User nutriologist = getCurrentUser();
        Patient patient = patientRepository
                .findByIdAndNutritionistId(id, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));

        patient.setFirstName(req.getFirstName());
        patient.setLastName(req.getLastName());
        patient.setEmail(req.getEmail());
        patient.setPhoneNumber(req.getPhoneNumber());
        patient.setDateOfBirth(req.getDateOfBirth());
        patient.setGender(parseEnum(Patient.Gender.class, req.getGender()));
        patient.setWeightKg(req.getWeightKg());
        patient.setHeightCm(req.getHeightCm());
        patient.setMedicalConditions(req.getMedicalConditions());
        patient.setAllergies(req.getAllergies());
        patient.setFoodPreferences(req.getFoodPreferences());
        patient.setFoodRestrictions(req.getFoodRestrictions());
        patient.setPrimaryGoal(parseEnum(Patient.Goal.class, req.getPrimaryGoal()));
        patient.setActivityLevel(parseEnum(Patient.ActivityLevel.class, req.getActivityLevel()));
        patient.setNotes(req.getNotes());

        Patient updated = patientRepository.save(patient);
        log.info("Patient updated: {}", updated.getFullName());
        return PatientResponse.from(updated);
    }

    // ── SOFT DELETE ───────────────────────────────────────────
    @Transactional
    public void deletePatient(Long id) {
        User nutriologist = getCurrentUser();
        Patient patient = patientRepository
                .findByIdAndNutritionistId(id, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));

        patient.setStatus(Patient.Status.INACTIVE);
        patientRepository.save(patient);
        log.info("Patient deactivated: {}", patient.getFullName());
    }

    // ── MEASUREMENTS ──────────────────────────────────────────
    @Transactional
    public MeasurementResponse addMeasurement(Long patientId, MeasurementRequest req) {
        User nutriologist = getCurrentUser();
        Patient patient = patientRepository
                .findByIdAndNutritionistId(patientId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        PatientMeasurement m = new PatientMeasurement();
        m.setPatient(patient);
        m.setMeasuredAt(req.getMeasuredAt());
        m.setWeightKg(req.getWeightKg());
        m.setHeightCm(req.getHeightCm());
        m.setWaistCm(req.getWaistCm());
        m.setHipCm(req.getHipCm());
        m.setBodyFatPercent(req.getBodyFatPercent());
        m.setMuscleMassKg(req.getMuscleMassKg());
        m.setNotes(req.getNotes());

        // Keep patient's current weight in sync
        if (req.getWeightKg() != null) {
            patient.setWeightKg(req.getWeightKg());
            patientRepository.save(patient);
        }

        PatientMeasurement saved = measurementRepository.save(m);
        log.info("Measurement added for patient: {}", patient.getFullName());
        return MeasurementResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<MeasurementResponse> getMeasurements(Long patientId) {
        User nutriologist = getCurrentUser();
        patientRepository.findByIdAndNutritionistId(patientId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        return measurementRepository
                .findByPatientIdOrderByMeasuredAtAsc(patientId)
                .stream()
                .map(MeasurementResponse::from)
                .collect(Collectors.toList());
    }

    // ── Portal password management ───────────────────────────
    @Transactional
    public Map<String,String> setPortalPassword(Long patientId, String plainPassword) {
        User nutritionist = getCurrentUser();
        Patient patient = patientRepository.findByIdAndNutritionistId(patientId, nutritionist.getId())
                .orElseThrow(() -> new com.nutriem.exception.ResourceNotFoundException("Patient", patientId));
        patient.setPortalPassword(passwordEncoder.encode(plainPassword));
        patientRepository.save(patient);
        return java.util.Map.of("message", "Portal password set successfully",
                                "email", patient.getEmail() != null ? patient.getEmail() : "");
    }

    @Transactional
    public Map<String,String> disablePortal(Long patientId) {
        User nutritionist = getCurrentUser();
        Patient patient = patientRepository.findByIdAndNutritionistId(patientId, nutritionist.getId())
                .orElseThrow(() -> new com.nutriem.exception.ResourceNotFoundException("Patient", patientId));
        patient.setPortalPassword(null);
        patientRepository.save(patient);
        return java.util.Map.of("message", "Portal access disabled");
    }

    public boolean isPortalEnabled(Long patientId) {
        User nutritionist = getCurrentUser();
        Patient patient = patientRepository.findByIdAndNutritionistId(patientId, nutritionist.getId())
                .orElseThrow(() -> new com.nutriem.exception.ResourceNotFoundException("Patient", patientId));
        return patient.getPortalPassword() != null;
    }

    // ── Helpers ───────────────────────────────────────────────
    private <E extends Enum<E>> E parseEnum(Class<E> enumClass, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid enum value '{}' for {}", value, enumClass.getSimpleName());
            return null;
        }
    }
}
