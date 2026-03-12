package com.nutriem.service;

import com.nutriem.dto.request.DietPlanRequest;
import com.nutriem.dto.request.MealRequest;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.dto.response.MealResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.DietPlan;
import com.nutriem.model.Meal;
import com.nutriem.model.Patient;
import com.nutriem.model.User;
import com.nutriem.repository.DietPlanRepository;
import com.nutriem.repository.MealRepository;
import com.nutriem.repository.PatientRepository;
import com.nutriem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietPlanService {

    private static final Logger log = LoggerFactory.getLogger(DietPlanService.class);

    private final DietPlanRepository dietPlanRepository;
    private final MealRepository     mealRepository;
    private final PatientRepository  patientRepository;
    private final UserRepository     userRepository;

    public DietPlanService(DietPlanRepository dietPlanRepository,
                           MealRepository mealRepository,
                           PatientRepository patientRepository,
                           UserRepository userRepository) {
        this.dietPlanRepository = dietPlanRepository;
        this.mealRepository     = mealRepository;
        this.patientRepository  = patientRepository;
        this.userRepository     = userRepository;
    }

    // ── Current user ──────────────────────────────────────────
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    // ── CREATE DIET PLAN ──────────────────────────────────────
    @Transactional
    public DietPlanResponse createDietPlan(DietPlanRequest req) {
        User nutriologist = getCurrentUser();

        Patient patient = patientRepository
                .findByIdAndNutritionistId(req.getPatientId(), nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", req.getPatientId()));

        DietPlan plan = new DietPlan();
        plan.setName(req.getName());
        plan.setDescription(req.getDescription());
        plan.setPatient(patient);
        plan.setCreatedBy(nutriologist);
        plan.setStartDate(req.getStartDate());
        plan.setEndDate(req.getEndDate());
        plan.setTargetCalories(req.getTargetCalories());
        plan.setTargetProteinG(req.getTargetProteinG());
        plan.setTargetCarbsG(req.getTargetCarbsG());
        plan.setTargetFatG(req.getTargetFatG());
        plan.setTargetFiberG(req.getTargetFiberG());
        plan.setAiPrompt(req.getAiPrompt());
        plan.setAiNotes(req.getAiNotes());

        if (req.getGenerationSource() != null) {
            try {
                plan.setGenerationSource(
                        DietPlan.GenerationSource.valueOf(req.getGenerationSource().toUpperCase()));
            } catch (IllegalArgumentException e) {
                plan.setGenerationSource(DietPlan.GenerationSource.MANUAL);
            }
        }

        DietPlan saved = dietPlanRepository.save(plan);
        log.info("Diet plan '{}' created for patient: {}", saved.getName(), patient.getFullName());
        return DietPlanResponse.from(saved);
    }

    // ── GET ALL PLANS FOR A PATIENT ───────────────────────────
    @Transactional(readOnly = true)
    public List<DietPlanResponse> getDietPlansByPatient(Long patientId) {
        User nutriologist = getCurrentUser();

        patientRepository.findByIdAndNutritionistId(patientId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        return dietPlanRepository.findAllByPatientId(patientId)
                .stream()
                .map(DietPlanResponse::from)
                .collect(Collectors.toList());
    }

    // ── GET SINGLE PLAN WITH MEALS ────────────────────────────
    @Transactional(readOnly = true)
    public DietPlanResponse getDietPlanById(Long planId) {
        User nutriologist = getCurrentUser();

        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        // Load meals explicitly to avoid lazy loading issues
        List<Meal> meals = mealRepository.findByDietPlanId(planId);
        plan.setMeals(meals);

        return DietPlanResponse.withMeals(plan);
    }

    // ── UPDATE DIET PLAN ──────────────────────────────────────
    @Transactional
    public DietPlanResponse updateDietPlan(Long planId, DietPlanRequest req) {
        User nutriologist = getCurrentUser();

        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        plan.setName(req.getName());
        plan.setDescription(req.getDescription());
        plan.setStartDate(req.getStartDate());
        plan.setEndDate(req.getEndDate());
        plan.setTargetCalories(req.getTargetCalories());
        plan.setTargetProteinG(req.getTargetProteinG());
        plan.setTargetCarbsG(req.getTargetCarbsG());
        plan.setTargetFatG(req.getTargetFatG());
        plan.setTargetFiberG(req.getTargetFiberG());
        plan.setAiNotes(req.getAiNotes());

        DietPlan updated = dietPlanRepository.save(plan);
        log.info("Diet plan updated: {}", updated.getName());
        return DietPlanResponse.from(updated);
    }

    // ── ARCHIVE PLAN (soft delete) ────────────────────────────
    @Transactional
    public void archiveDietPlan(Long planId) {
        User nutriologist = getCurrentUser();

        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        plan.setStatus(DietPlan.Status.ARCHIVED);
        dietPlanRepository.save(plan);
        log.info("Diet plan archived: {}", plan.getName());
    }

    // ── ADD MEAL TO PLAN ──────────────────────────────────────
    @Transactional
    public MealResponse addMeal(Long planId, MealRequest req) {
        User nutriologist = getCurrentUser();

        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        Meal meal = new Meal();
        meal.setDietPlan(plan);
        meal.setName(req.getName());
        meal.setDayOfWeek(req.getDayOfWeek());
        meal.setInstructions(req.getInstructions());
        meal.setNotes(req.getNotes());
        meal.setTotalCalories(req.getTotalCalories());
        meal.setTotalProteinG(req.getTotalProteinG());
        meal.setTotalCarbsG(req.getTotalCarbsG());
        meal.setTotalFatG(req.getTotalFatG());
        meal.setTotalFiberG(req.getTotalFiberG());

        if (req.getMealType() != null) {
            try {
                meal.setMealType(Meal.MealType.valueOf(req.getMealType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                meal.setMealType(Meal.MealType.BREAKFAST);
            }
        }

        Meal saved = mealRepository.save(meal);
        log.info("Meal '{}' added to plan: {}", saved.getName(), plan.getName());
        return MealResponse.from(saved);
    }

    // ── GET MEALS FOR PLAN ────────────────────────────────────
    @Transactional(readOnly = true)
    public List<MealResponse> getMeals(Long planId) {
        User nutriologist = getCurrentUser();

        dietPlanRepository.findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        return mealRepository.findByDietPlanId(planId)
                .stream()
                .map(MealResponse::from)
                .collect(Collectors.toList());
    }

    // ── DELETE MEAL ───────────────────────────────────────────
    @Transactional
    public void deleteMeal(Long planId, Long mealId) {
        User nutriologist = getCurrentUser();

        dietPlanRepository.findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));

        Meal meal = mealRepository.findByIdAndDietPlanId(mealId, planId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal", mealId));

        mealRepository.delete(meal);
        log.info("Meal deleted: {}", meal.getName());
    }
}
