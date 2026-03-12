package com.nutriem.repository;

import com.nutriem.model.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealFoodRepository extends JpaRepository<MealFood, Long> {
    List<MealFood> findByMealId(Long mealId);
}
