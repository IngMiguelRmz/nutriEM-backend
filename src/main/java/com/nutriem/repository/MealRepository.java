package com.nutriem.repository;

import com.nutriem.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    @Query("SELECT DISTINCT m FROM Meal m LEFT JOIN FETCH m.mealFoods WHERE m.dietPlan.id = :planId ORDER BY m.dayOfWeek ASC, m.mealType ASC")
    List<Meal> findByDietPlanId(@Param("planId") Long dietPlanId);

    @Query("SELECT m FROM Meal m WHERE m.id = :id AND m.dietPlan.id = :planId")
    Optional<Meal> findByIdAndDietPlanId(@Param("id") Long id, @Param("planId") Long dietPlanId);
}
