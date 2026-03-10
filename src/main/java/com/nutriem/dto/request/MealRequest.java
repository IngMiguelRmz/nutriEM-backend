package com.nutriem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MealRequest {

    @NotBlank(message = "Meal name is required")
    private String name;

    @NotNull(message = "Meal type is required")
    private String mealType; // BREAKFAST, MORNING_SNACK, LUNCH, AFTERNOON_SNACK, DINNER, EVENING_SNACK

    // 1=Monday ... 7=Sunday, null = every day
    private Integer dayOfWeek;

    private String  instructions;
    private String  notes;

    // Optional pre-calculated totals (can be computed later from MealFoods)
    private Integer totalCalories;
    private Double  totalProteinG;
    private Double  totalCarbsG;
    private Double  totalFatG;
    private Double  totalFiberG;

    public MealRequest() {}

    public String getName()                     { return name; }
    public void setName(String v)               { this.name = v; }
    public String getMealType()                 { return mealType; }
    public void setMealType(String v)           { this.mealType = v; }
    public Integer getDayOfWeek()               { return dayOfWeek; }
    public void setDayOfWeek(Integer v)         { this.dayOfWeek = v; }
    public String getInstructions()             { return instructions; }
    public void setInstructions(String v)       { this.instructions = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
    public Integer getTotalCalories()           { return totalCalories; }
    public void setTotalCalories(Integer v)     { this.totalCalories = v; }
    public Double getTotalProteinG()            { return totalProteinG; }
    public void setTotalProteinG(Double v)      { this.totalProteinG = v; }
    public Double getTotalCarbsG()              { return totalCarbsG; }
    public void setTotalCarbsG(Double v)        { this.totalCarbsG = v; }
    public Double getTotalFatG()                { return totalFatG; }
    public void setTotalFatG(Double v)          { this.totalFatG = v; }
    public Double getTotalFiberG()              { return totalFiberG; }
    public void setTotalFiberG(Double v)        { this.totalFiberG = v; }
}
