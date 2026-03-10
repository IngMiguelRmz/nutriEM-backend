package com.nutriem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_foods")
public class MealFood {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false) private Double quantityGrams;
    private String servingDescription;
    private Double calories, proteinG, carbsG, fatG, fiberG;

    @Column(columnDefinition = "TEXT") private String notes;

    public MealFood() {}

    public void calculateNutrition() {
        if (food == null || quantityGrams == null) return;
        double r = quantityGrams / 100.0;
        this.calories = food.getCaloriesPer100g() != null ? food.getCaloriesPer100g() * r : null;
        this.proteinG = food.getProteinPer100g()  != null ? food.getProteinPer100g()  * r : null;
        this.carbsG   = food.getCarbsPer100g()    != null ? food.getCarbsPer100g()    * r : null;
        this.fatG     = food.getFatPer100g()      != null ? food.getFatPer100g()      * r : null;
        this.fiberG   = food.getFiberPer100g()    != null ? food.getFiberPer100g()    * r : null;
    }

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public Meal getMeal()                       { return meal; }
    public void setMeal(Meal v)                 { this.meal = v; }
    public Food getFood()                       { return food; }
    public void setFood(Food v)                 { this.food = v; }
    public Double getQuantityGrams()            { return quantityGrams; }
    public void setQuantityGrams(Double v)      { this.quantityGrams = v; }
    public String getServingDescription()       { return servingDescription; }
    public void setServingDescription(String v) { this.servingDescription = v; }
    public Double getCalories()                 { return calories; }
    public void setCalories(Double v)           { this.calories = v; }
    public Double getProteinG()                 { return proteinG; }
    public void setProteinG(Double v)           { this.proteinG = v; }
    public Double getCarbsG()                   { return carbsG; }
    public void setCarbsG(Double v)             { this.carbsG = v; }
    public Double getFatG()                     { return fatG; }
    public void setFatG(Double v)               { this.fatG = v; }
    public Double getFiberG()                   { return fiberG; }
    public void setFiberG(Double v)             { this.fiberG = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
}
