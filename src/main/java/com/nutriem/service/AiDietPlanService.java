package com.nutriem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.nutriem.dto.request.AiDietPlanRequest;
import com.nutriem.dto.response.DietPlanResponse;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiDietPlanService {

    private static final Logger log = LoggerFactory.getLogger(AiDietPlanService.class);

    private final ClaudeAiService    claudeAiService;
    private final DietPlanRepository dietPlanRepository;
    private final MealRepository     mealRepository;
    private final PatientRepository  patientRepository;
    private final UserRepository     userRepository;

    public AiDietPlanService(ClaudeAiService claudeAiService,
                              DietPlanRepository dietPlanRepository,
                              MealRepository mealRepository,
                              PatientRepository patientRepository,
                              UserRepository userRepository) {
        this.claudeAiService    = claudeAiService;
        this.dietPlanRepository = dietPlanRepository;
        this.mealRepository     = mealRepository;
        this.patientRepository  = patientRepository;
        this.userRepository     = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    @Transactional
    public DietPlanResponse generateAndSaveDietPlan(AiDietPlanRequest req) {
        User nutriologist = getCurrentUser();

        // 1. Load patient (only if belongs to this nutriologist)
        Patient patient = patientRepository
                .findByIdAndNutritionistId(req.getPatientId(), nutriologist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", req.getPatientId()));

        log.info("Generating AI diet plan for patient: {} ({})", patient.getFullName(), patient.getId());

        // 2. Call Claude
        JsonNode aiResponse = claudeAiService.generateDietPlan(
                patient,
                req.getDays(),
                req.getTargetCalories(),
                req.getCustomInstructions()
        );

        // 3. Build DietPlan entity from Claude response
        DietPlan plan = new DietPlan();
        plan.setPatient(patient);
        plan.setCreatedBy(nutriologist);
        plan.setGenerationSource(DietPlan.GenerationSource.AI_GENERATED);
        plan.setStatus(DietPlan.Status.ACTIVE);
        plan.setStartDate(LocalDate.now());
        plan.setEndDate(LocalDate.now().plusDays(req.getDays()));
        plan.setAiPrompt(req.getCustomInstructions());

        // Use custom name if provided, otherwise use Claude's suggestion
        String planName = (req.getPlanName() != null && !req.getPlanName().isBlank())
                ? req.getPlanName()
                : getTextSafely(aiResponse, "planName", "AI Diet Plan - " + patient.getFirstName());
        plan.setName(planName);
        plan.setDescription(getTextSafely(aiResponse, "planDescription", null));
        plan.setAiNotes(getTextSafely(aiResponse, "aiNotes", null));

        // Nutritional targets
        plan.setTargetCalories(getIntSafely(aiResponse, "targetCalories", req.getTargetCalories()));
        plan.setTargetProteinG(getIntSafely(aiResponse, "targetProteinG", null));
        plan.setTargetCarbsG(getIntSafely(aiResponse, "targetCarbsG", null));
        plan.setTargetFatG(getIntSafely(aiResponse, "targetFatG", null));
        plan.setTargetFiberG(getIntSafely(aiResponse, "targetFiberG", null));

        DietPlan savedPlan = dietPlanRepository.save(plan);

        // 4. Parse and save meals
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

                    // Parse meal type safely
                    String mealTypeStr = getTextSafely(mealNode, "mealType", "BREAKFAST");
                    try {
                        meal.setMealType(Meal.MealType.valueOf(mealTypeStr.toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        meal.setMealType(Meal.MealType.BREAKFAST);
                    }

                    savedMeals.add(mealRepository.save(meal));
                } catch (Exception e) {
                    log.warn("Failed to parse meal node: {}", mealNode, e);
                }
            }
        }

        log.info("AI diet plan saved: '{}' with {} meals", savedPlan.getName(), savedMeals.size());

        // 5. Return full plan with meals
        savedPlan.setMeals(savedMeals);
        return DietPlanResponse.withMeals(savedPlan);
    }

    // ── Safe JSON field extractors ────────────────────────────
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
