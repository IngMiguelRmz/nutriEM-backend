package com.nutriem.service;

import com.nutriem.dto.request.DietPlanRequest;
import com.nutriem.dto.request.IngredientRequest;
import com.nutriem.dto.request.MealRequest;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.dto.response.MealResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.*;
import com.nutriem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DietPlanService {

    private static final Logger log = LoggerFactory.getLogger(DietPlanService.class);

    private final DietPlanRepository dietPlanRepository;
    private final MealRepository     mealRepository;
    private final MealFoodRepository mealFoodRepository;
    private final FoodRepository     foodRepository;
    private final PatientRepository  patientRepository;
    private final UserRepository     userRepository;

    public DietPlanService(DietPlanRepository dietPlanRepository,
                           MealRepository mealRepository,
                           MealFoodRepository mealFoodRepository,
                           FoodRepository foodRepository,
                           PatientRepository patientRepository,
                           UserRepository userRepository) {
        this.dietPlanRepository = dietPlanRepository;
        this.mealRepository     = mealRepository;
        this.mealFoodRepository = mealFoodRepository;
        this.foodRepository     = foodRepository;
        this.patientRepository  = patientRepository;
        this.userRepository     = userRepository;
    }

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
                plan.setGenerationSource(DietPlan.GenerationSource.valueOf(req.getGenerationSource().toUpperCase()));
            } catch (IllegalArgumentException e) {
                plan.setGenerationSource(DietPlan.GenerationSource.MANUAL);
            }
        } else {
            plan.setGenerationSource(DietPlan.GenerationSource.MANUAL);
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
                .stream().map(DietPlanResponse::from).collect(Collectors.toList());
    }

    // ── GET SINGLE PLAN WITH MEALS ────────────────────────────
    @Transactional(readOnly = true)
    public DietPlanResponse getDietPlanById(Long planId) {
        User nutriologist = getCurrentUser();
        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));
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

        return DietPlanResponse.from(dietPlanRepository.save(plan));
    }

    // ── ARCHIVE PLAN ──────────────────────────────────────────
    @Transactional
    public void archiveDietPlan(Long planId) {
        User nutriologist = getCurrentUser();
        DietPlan plan = dietPlanRepository
                .findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));
        plan.setStatus(DietPlan.Status.ARCHIVED);
        dietPlanRepository.save(plan);
    }

    // ── ADD MEAL WITH INGREDIENTS ─────────────────────────────
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

        if (req.getMealType() != null) {
            try { meal.setMealType(Meal.MealType.valueOf(req.getMealType().toUpperCase())); }
            catch (IllegalArgumentException e) { meal.setMealType(Meal.MealType.BREAKFAST); }
        }

        // If ingredients provided, compute totals from them; otherwise use explicit totals
        if (req.getIngredients() != null && !req.getIngredients().isEmpty()) {
            double kcal = 0, prot = 0, carbs = 0, fat = 0, fiber = 0;
            for (IngredientRequest ing : req.getIngredients()) {
                if (ing.getCalories()  != null) kcal  += ing.getCalories();
                if (ing.getProteinG()  != null) prot  += ing.getProteinG();
                if (ing.getCarbsG()    != null) carbs += ing.getCarbsG();
                if (ing.getFatG()      != null) fat   += ing.getFatG();
                if (ing.getFiberG()    != null) fiber += ing.getFiberG();
            }
            meal.setTotalCalories((int) Math.round(kcal));
            meal.setTotalProteinG(Math.round(prot  * 10.0) / 10.0);
            meal.setTotalCarbsG(  Math.round(carbs * 10.0) / 10.0);
            meal.setTotalFatG(    Math.round(fat   * 10.0) / 10.0);
            meal.setTotalFiberG(  Math.round(fiber * 10.0) / 10.0);
        } else {
            meal.setTotalCalories(req.getTotalCalories());
            meal.setTotalProteinG(req.getTotalProteinG());
            meal.setTotalCarbsG(req.getTotalCarbsG());
            meal.setTotalFatG(req.getTotalFatG());
            meal.setTotalFiberG(req.getTotalFiberG());
        }

        Meal savedMeal = mealRepository.save(meal);

        // Save ingredients
        Set<MealFood> savedIngredients = new HashSet<>();
        if (req.getIngredients() != null) {
            for (IngredientRequest ing : req.getIngredients()) {
                MealFood mf = new MealFood();
                mf.setMeal(savedMeal);
                mf.setQuantityGrams(ing.getQuantityGrams() != null ? ing.getQuantityGrams() : 100.0);
                mf.setServingDescription(ing.getServingDescription());
                mf.setCalories(ing.getCalories());
                mf.setProteinG(ing.getProteinG());
                mf.setCarbsG(ing.getCarbsG());
                mf.setFatG(ing.getFatG());
                mf.setFiberG(ing.getFiberG());
                mf.setImageUrl(ing.getImageUrl());

                if (ing.getFoodId() != null) {
                    // Linked to food database
                    foodRepository.findById(ing.getFoodId()).ifPresent(food -> {
                        mf.setFood(food);
                        // Calculate nutrition from food DB if not provided
                        if (mf.getCalories() == null) mf.calculateNutrition();
                    });
                } else {
                    mf.setIngredientName(ing.getIngredientName());
                }
                savedIngredients.add(mealFoodRepository.save(mf));
            }
        }

        savedMeal.setMealFoods(savedIngredients);
        log.info("Meal '{}' added to plan '{}' with {} ingredients",
                savedMeal.getName(), plan.getName(), savedIngredients.size());
        return MealResponse.from(savedMeal);
    }

    // ── GET MEALS FOR PLAN ────────────────────────────────────
    @Transactional(readOnly = true)
    public List<MealResponse> getMeals(Long planId) {
        User nutriologist = getCurrentUser();
        dietPlanRepository.findByIdAndNutritionistId(planId, nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("DietPlan", planId));
        return mealRepository.findByDietPlanId(planId).stream()
                .map(MealResponse::from).collect(Collectors.toList());
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
    }
}
