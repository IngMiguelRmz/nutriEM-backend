package com.nutriem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nutriem.dto.request.AiDietPlanRequest;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.*;
import com.nutriem.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AiDietPlanService {

    private static final Logger log = LoggerFactory.getLogger(AiDietPlanService.class);

    private final ClaudeAiService          claudeAiService;
    private final DietPlanRepository       dietPlanRepository;
    private final MealRepository           mealRepository;
    private final MealFoodRepository       mealFoodRepository;
    private final PatientRepository        patientRepository;
    private final UserRepository           userRepository;
    private final ClinicalHistoryRepository clinicalHistoryRepository;

    public AiDietPlanService(ClaudeAiService claudeAiService,
                              DietPlanRepository dietPlanRepository,
                              MealRepository mealRepository,
                              MealFoodRepository mealFoodRepository,
                              PatientRepository patientRepository,
                              UserRepository userRepository,
                              ClinicalHistoryRepository clinicalHistoryRepository) {
        this.claudeAiService           = claudeAiService;
        this.dietPlanRepository        = dietPlanRepository;
        this.mealRepository            = mealRepository;
        this.mealFoodRepository        = mealFoodRepository;
        this.patientRepository         = patientRepository;
        this.userRepository            = userRepository;
        this.clinicalHistoryRepository = clinicalHistoryRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    @Transactional
    public DietPlanResponse generateAndSaveDietPlan(AiDietPlanRequest req) {
        User nutriologist = getCurrentUser();

        Patient patient = patientRepository
                .findByIdAndNutritionistId(req.getPatientId(), nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", req.getPatientId()));

        log.info("Generating AI diet plan for patient: {} ({})", patient.getFullName(), patient.getId());

        com.nutriem.model.ClinicalHistory history =
                clinicalHistoryRepository.findByPatientId(patient.getId()).orElse(null);

        JsonNode aiResponse = claudeAiService.generateDietPlan(
                patient, history, req.getDays(), req.getTargetCalories(), req.getCustomInstructions());

        // Build and save plan
        DietPlan plan = new DietPlan();
        plan.setPatient(patient);
        plan.setCreatedBy(nutriologist);
        plan.setGenerationSource(DietPlan.GenerationSource.AI_GENERATED);
        plan.setStatus(DietPlan.Status.ACTIVE);
        plan.setStartDate(LocalDate.now());
        plan.setEndDate(LocalDate.now().plusDays(req.getDays()));
        plan.setAiPrompt(req.getCustomInstructions());

        String planName = (req.getPlanName() != null && !req.getPlanName().isBlank())
                ? req.getPlanName()
                : getTextSafely(aiResponse, "planName", "AI Diet Plan - " + patient.getFirstName());
        plan.setName(planName);
        plan.setDescription(getTextSafely(aiResponse, "planDescription", null));
        plan.setAiNotes(getTextSafely(aiResponse, "aiNotes", null));
        plan.setTargetCalories(getIntSafely(aiResponse, "targetCalories", req.getTargetCalories()));
        plan.setTargetProteinG(getIntSafely(aiResponse, "targetProteinG", null));
        plan.setTargetCarbsG(getIntSafely(aiResponse, "targetCarbsG", null));
        plan.setTargetFatG(getIntSafely(aiResponse, "targetFatG", null));
        plan.setTargetFiberG(getIntSafely(aiResponse, "targetFiberG", null));

        DietPlan savedPlan = dietPlanRepository.save(plan);

        // Parse meals and ingredients
        List<Meal> savedMeals = new ArrayList<>();
        JsonNode mealsNode = aiResponse.path("meals");

        if (mealsNode.isArray()) {
            for (JsonNode mealNode : mealsNode) {
                try {
                    Meal meal = new Meal();
                    meal.setDietPlan(savedPlan);
                    meal.setName(getTextSafely(mealNode, "name", "Meal"));
                    meal.setInstructions(getTextSafely(mealNode, "instructions", null));
                    meal.setDayOfWeek(getIntSafely(mealNode, "dayOfWeek", 1));
                    meal.setTotalCalories(getIntSafely(mealNode, "totalCalories", null));
                    meal.setTotalProteinG(getDoubleSafely(mealNode, "totalProteinG", null));
                    meal.setTotalCarbsG(getDoubleSafely(mealNode, "totalCarbsG", null));
                    meal.setTotalFatG(getDoubleSafely(mealNode, "totalFatG", null));
                    meal.setTotalFiberG(getDoubleSafely(mealNode, "totalFiberG", null));

                    String mealTypeStr = getTextSafely(mealNode, "mealType", "BREAKFAST");
                    try {
                        meal.setMealType(Meal.MealType.valueOf(mealTypeStr.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        meal.setMealType(Meal.MealType.BREAKFAST);
                    }

                    Meal savedMeal = mealRepository.save(meal);

                    // Parse and save ingredients
                    JsonNode ingredientsNode = mealNode.path("ingredients");
                    Set<MealFood> savedIngredients = new HashSet<>();

                    if (ingredientsNode.isArray()) {
                        for (JsonNode ing : ingredientsNode) {
                            try {
                                MealFood mf = new MealFood();
                                mf.setMeal(savedMeal);
                                mf.setIngredientName(getTextSafely(ing, "name", "Ingredient"));
                                mf.setQuantityGrams(getDoubleSafely(ing, "quantityGrams", 100.0));
                                mf.setServingDescription(getTextSafely(ing, "servingDescription", null));
                                mf.setCalories(getDoubleSafely(ing, "calories", null));
                                mf.setProteinG(getDoubleSafely(ing, "proteinG", null));
                                mf.setCarbsG(getDoubleSafely(ing, "carbsG", null));
                                mf.setFatG(getDoubleSafely(ing, "fatG", null));
                                mf.setFiberG(getDoubleSafely(ing, "fiberG", null));
                                savedIngredients.add(mealFoodRepository.save(mf));
                            } catch (Exception e) {
                                log.warn("Failed to parse ingredient: {}", ing, e);
                            }
                        }
                    }

                    savedMeal.setMealFoods(savedIngredients);
                    savedMeals.add(savedMeal);
                    log.debug("Saved meal '{}' with {} ingredients", savedMeal.getName(), savedIngredients.size());

                } catch (Exception e) {
                    log.warn("Failed to parse meal node: {}", mealNode, e);
                }
            }
        }

        log.info("AI plan saved: '{}' — {} meals", savedPlan.getName(), savedMeals.size());

        savedPlan.setMeals(savedMeals);
        return DietPlanResponse.withMeals(savedPlan);
    }

    private String getTextSafely(JsonNode node, String field, String defaultVal) {
        JsonNode n = node.path(field);
        return (n.isMissingNode() || n.isNull()) ? defaultVal : n.asText();
    }

    private Integer getIntSafely(JsonNode node, String field, Integer defaultVal) {
        JsonNode n = node.path(field);
        return (n.isMissingNode() || n.isNull()) ? defaultVal : n.asInt();
    }

    private Double getDoubleSafely(JsonNode node, String field, Double defaultVal) {
        JsonNode n = node.path(field);
        return (n.isMissingNode() || n.isNull()) ? defaultVal : n.asDouble();
    }
}
