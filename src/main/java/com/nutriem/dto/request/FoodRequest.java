package com.nutriem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FoodRequest {
    @NotBlank private String name;
    private String category;
    @NotNull  private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
    private Double fiberPer100g;
    private String servingDescription;
    private Double servingGrams;

    public String getName()                       { return name; }
    public void   setName(String v)               { this.name = v; }
    public String getCategory()                   { return category; }
    public void   setCategory(String v)           { this.category = v; }
    public Double getCaloriesPer100g()            { return caloriesPer100g; }
    public void   setCaloriesPer100g(Double v)    { this.caloriesPer100g = v; }
    public Double getProteinPer100g()             { return proteinPer100g; }
    public void   setProteinPer100g(Double v)     { this.proteinPer100g = v; }
    public Double getCarbsPer100g()               { return carbsPer100g; }
    public void   setCarbsPer100g(Double v)       { this.carbsPer100g = v; }
    public Double getFatPer100g()                 { return fatPer100g; }
    public void   setFatPer100g(Double v)         { this.fatPer100g = v; }
    public Double getFiberPer100g()               { return fiberPer100g; }
    public void   setFiberPer100g(Double v)       { this.fiberPer100g = v; }
    public String getServingDescription()         { return servingDescription; }
    public void   setServingDescription(String v) { this.servingDescription = v; }
    public Double getServingGrams()               { return servingGrams; }
    public void   setServingGrams(Double v)       { this.servingGrams = v; }
}
