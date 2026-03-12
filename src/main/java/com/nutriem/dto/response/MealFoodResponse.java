package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.MealFood;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MealFoodResponse {

    private Long   id;
    private String ingredientName;
    private Double quantityGrams;
    private String servingDescription;
    private Double calories;
    private Double proteinG;
    private Double carbsG;
    private Double fatG;
    private Double fiberG;

    public MealFoodResponse() {}

    public static MealFoodResponse from(MealFood mf) {
        MealFoodResponse r = new MealFoodResponse();
        r.id                 = mf.getId();
        r.ingredientName     = mf.getDisplayName();
        r.quantityGrams      = mf.getQuantityGrams();
        r.servingDescription = mf.getServingDescription();
        r.calories           = mf.getCalories();
        r.proteinG           = mf.getProteinG();
        r.carbsG             = mf.getCarbsG();
        r.fatG               = mf.getFatG();
        r.fiberG             = mf.getFiberG();
        return r;
    }

    public Long   getId()                         { return id; }
    public String getIngredientName()             { return ingredientName; }
    public Double getQuantityGrams()              { return quantityGrams; }
    public String getServingDescription()         { return servingDescription; }
    public Double getCalories()                   { return calories; }
    public Double getProteinG()                   { return proteinG; }
    public Double getCarbsG()                     { return carbsG; }
    public Double getFatG()                       { return fatG; }
    public Double getFiberG()                     { return fiberG; }
}
