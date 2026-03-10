package com.nutriem.controller;

import com.nutriem.dto.request.DietPlanRequest;
import com.nutriem.dto.request.MealRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.dto.response.MealResponse;
import com.nutriem.service.DietPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DietPlanController {

    private final DietPlanService dietPlanService;

    public DietPlanController(DietPlanService dietPlanService) {
        this.dietPlanService = dietPlanService;
    }

    // POST /api/diet-plans
    @PostMapping("/diet-plans")
    public ResponseEntity<ApiResponse<DietPlanResponse>> createDietPlan(
            @Valid @RequestBody DietPlanRequest req) {
        DietPlanResponse response = dietPlanService.createDietPlan(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Diet plan created successfully", response));
    }

    // GET /api/patients/{patientId}/diet-plans
    @GetMapping("/patients/{patientId}/diet-plans")
    public ResponseEntity<ApiResponse<List<DietPlanResponse>>> getDietPlansByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                ApiResponse.ok(dietPlanService.getDietPlansByPatient(patientId)));
    }

    // GET /api/diet-plans/{id}  (includes meals)
    @GetMapping("/diet-plans/{id}")
    public ResponseEntity<ApiResponse<DietPlanResponse>> getDietPlan(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(dietPlanService.getDietPlanById(id)));
    }

    // PUT /api/diet-plans/{id}
    @PutMapping("/diet-plans/{id}")
    public ResponseEntity<ApiResponse<DietPlanResponse>> updateDietPlan(
            @PathVariable Long id,
            @Valid @RequestBody DietPlanRequest req) {
        return ResponseEntity.ok(
                ApiResponse.ok("Diet plan updated", dietPlanService.updateDietPlan(id, req)));
    }

    // DELETE /api/diet-plans/{id}  (archive)
    @DeleteMapping("/diet-plans/{id}")
    public ResponseEntity<ApiResponse<Void>> archiveDietPlan(@PathVariable Long id) {
        dietPlanService.archiveDietPlan(id);
        return ResponseEntity.ok(ApiResponse.ok("Diet plan archived", null));
    }

    // POST /api/diet-plans/{id}/meals
    @PostMapping("/diet-plans/{id}/meals")
    public ResponseEntity<ApiResponse<MealResponse>> addMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealRequest req) {
        MealResponse response = dietPlanService.addMeal(id, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Meal added successfully", response));
    }

    // GET /api/diet-plans/{id}/meals
    @GetMapping("/diet-plans/{id}/meals")
    public ResponseEntity<ApiResponse<List<MealResponse>>> getMeals(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(dietPlanService.getMeals(id)));
    }

    // DELETE /api/diet-plans/{planId}/meals/{mealId}
    @DeleteMapping("/diet-plans/{planId}/meals/{mealId}")
    public ResponseEntity<ApiResponse<Void>> deleteMeal(
            @PathVariable Long planId,
            @PathVariable Long mealId) {
        dietPlanService.deleteMeal(planId, mealId);
        return ResponseEntity.ok(ApiResponse.ok("Meal deleted", null));
    }
}
