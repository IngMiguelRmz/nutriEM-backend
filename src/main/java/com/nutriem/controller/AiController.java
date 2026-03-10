package com.nutriem.controller;

import com.nutriem.dto.request.AiDietPlanRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.service.AiDietPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiDietPlanService aiDietPlanService;

    public AiController(AiDietPlanService aiDietPlanService) {
        this.aiDietPlanService = aiDietPlanService;
    }

    /**
     * POST /api/ai/generate-diet-plan
     * Generates a full AI diet plan using Claude and saves it to the database.
     */
    @PostMapping("/generate-diet-plan")
    public ResponseEntity<ApiResponse<DietPlanResponse>> generateDietPlan(
            @Valid @RequestBody AiDietPlanRequest req) {
        DietPlanResponse response = aiDietPlanService.generateAndSaveDietPlan(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("AI diet plan generated successfully", response));
    }
}
