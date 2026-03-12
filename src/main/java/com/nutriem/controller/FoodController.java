package com.nutriem.controller;

import com.nutriem.dto.request.FoodRequest;
import com.nutriem.dto.response.ApiResponse;
import com.nutriem.dto.response.FoodResponse;
import com.nutriem.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // GET /api/foods?q=chicken
    @GetMapping
    public ResponseEntity<ApiResponse<List<FoodResponse>>> search(
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(ApiResponse.ok(foodService.search(q)));
    }

    // GET /api/foods/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<FoodResponse>>> byCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(ApiResponse.ok(foodService.getByCategory(category)));
    }

    // POST /api/foods  — add custom food
    @PostMapping
    public ResponseEntity<ApiResponse<FoodResponse>> create(
            @Valid @RequestBody FoodRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Food created", foodService.create(req)));
    }
}
