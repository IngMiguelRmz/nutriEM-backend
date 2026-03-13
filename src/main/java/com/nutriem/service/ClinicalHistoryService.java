package com.nutriem.service;

import com.nutriem.dto.request.ClinicalHistoryRequest;
import com.nutriem.dto.response.ClinicalHistoryResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.ClinicalHistory;
import com.nutriem.model.Patient;
import com.nutriem.repository.ClinicalHistoryRepository;
import com.nutriem.repository.PatientRepository;
import com.nutriem.repository.UserRepository;
import com.nutriem.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicalHistoryService {

    private final ClinicalHistoryRepository repo;
    private final PatientRepository         patientRepository;
    private final UserRepository            userRepository;

    public ClinicalHistoryService(ClinicalHistoryRepository repo,
                                   PatientRepository patientRepository,
                                   UserRepository userRepository) {
        this.repo              = repo;
        this.patientRepository = patientRepository;
        this.userRepository    = userRepository;
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional(readOnly = true)
    public ClinicalHistoryResponse get(Long patientId) {
        User user = currentUser();
        patientRepository.findByIdAndNutritionistId(patientId, user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));
        return repo.findByPatientId(patientId)
            .map(ClinicalHistoryResponse::from)
            .orElse(null); // null = not yet created, frontend shows empty form
    }

    @Transactional
    public ClinicalHistoryResponse upsert(Long patientId, ClinicalHistoryRequest req) {
        User user = currentUser();
        Patient patient = patientRepository.findByIdAndNutritionistId(patientId, user.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        ClinicalHistory h = repo.findByPatientId(patientId).orElseGet(() -> {
            ClinicalHistory newH = new ClinicalHistory();
            newH.setPatient(patient);
            return newH;
        });

        mapFields(h, req);
        return ClinicalHistoryResponse.from(repo.save(h));
    }

    private void mapFields(ClinicalHistory h, ClinicalHistoryRequest r) {
        h.setChronicDiseases(r.getChronicDiseases());
        h.setFamilyHistory(r.getFamilyHistory());
        h.setSurgicalHistory(r.getSurgicalHistory());
        h.setCurrentMedications(r.getCurrentMedications());
        h.setMealsPerDay(r.getMealsPerDay());
        h.setSkipsBreakfast(r.getSkipsBreakfast());
        h.setEatsLateNight(r.getEatsLateNight());
        h.setFoodAllergies(r.getFoodAllergies());
        h.setFoodIntolerances(r.getFoodIntolerances());
        h.setFoodAversions(r.getFoodAversions());
        h.setFoodPreferences(r.getFoodPreferences());
        h.setDietaryRestrictions(r.getDietaryRestrictions());
        h.setConsumesAlcohol(r.getConsumesAlcohol());
        h.setAlcoholFrequency(r.getAlcoholFrequency());
        h.setConsumesCaffeine(r.getConsumesCaffeine());
        h.setWaterIntakeLiters(r.getWaterIntakeLiters());
        h.setHasBloating(r.getHasBloating());
        h.setHasConstipation(r.getHasConstipation());
        h.setHasDiarrhea(r.getHasDiarrhea());
        h.setHasAcidReflux(r.getHasAcidReflux());
        h.setHasIrritable(r.getHasIrritable());
        h.setDigestiveNotes(r.getDigestiveNotes());
        h.setSleepHoursPerNight(r.getSleepHoursPerNight());
        h.setStressLevel(r.getStressLevel());
        h.setSmoker(r.getSmoker());
        h.setPhysicalActivityDetail(r.getPhysicalActivityDetail());
        h.setOccupation(r.getOccupation());
        h.setFastingGlucose(r.getFastingGlucose());
        h.setHba1c(r.getHba1c());
        h.setTotalCholesterol(r.getTotalCholesterol());
        h.setLdlCholesterol(r.getLdlCholesterol());
        h.setHdlCholesterol(r.getHdlCholesterol());
        h.setTriglycerides(r.getTriglycerides());
        h.setHemoglobin(r.getHemoglobin());
        h.setFerritin(r.getFerritin());
        h.setVitaminD(r.getVitaminD());
        h.setVitaminB12(r.getVitaminB12());
        h.setOtherLabResults(r.getOtherLabResults());
        h.setWeightGoalDetail(r.getWeightGoalDetail());
        h.setMotivations(r.getMotivations());
        h.setPreviousDietAttempts(r.getPreviousDietAttempts());
        h.setNutritionistNotes(r.getNutritionistNotes());
    }
}
