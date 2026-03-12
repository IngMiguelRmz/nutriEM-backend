package com.nutriem.service;

import com.nutriem.dto.request.FoodRequest;
import com.nutriem.dto.response.FoodResponse;
import com.nutriem.model.Food;
import com.nutriem.repository.FoodRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<FoodResponse> search(String query) {
        if (query == null || query.isBlank()) {
            return foodRepository.findAll().stream()
                    .limit(50)
                    .map(FoodResponse::from)
                    .collect(Collectors.toList());
        }
        return foodRepository.searchByName(query.trim()).stream()
                .limit(30)
                .map(FoodResponse::from)
                .collect(Collectors.toList());
    }

    public List<FoodResponse> getByCategory(String category) {
        Food.Category cat = Food.Category.valueOf(category.toUpperCase());
        return foodRepository.findByCategory(cat).stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
    }

    public FoodResponse create(FoodRequest req) {
        Food food = new Food();
        food.setName(req.getName());
        food.setCaloriesPer100g(req.getCaloriesPer100g());
        food.setProteinPer100g(req.getProteinPer100g());
        food.setCarbsPer100g(req.getCarbsPer100g());
        food.setFatPer100g(req.getFatPer100g());
        food.setFiberPer100g(req.getFiberPer100g());
        food.setServingDescription(req.getServingDescription());
        food.setServingGrams(req.getServingGrams());
        food.setVerified(false);
        if (req.getCategory() != null) {
            try { food.setCategory(Food.Category.valueOf(req.getCategory().toUpperCase())); }
            catch (IllegalArgumentException ignored) {}
        }
        return FoodResponse.from(foodRepository.save(food));
    }
}
