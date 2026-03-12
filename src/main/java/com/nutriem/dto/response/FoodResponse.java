package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.Food;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodResponse {
    private Long   id;
    private String name;
    private String category;
    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
    private Double fiberPer100g;
    private String servingDescription;
    private Double servingGrams;
    private boolean verified;

    public static FoodResponse from(Food f) {
        FoodResponse r = new FoodResponse();
        r.id                 = f.getId();
        r.name               = f.getName();
        r.category           = f.getCategory() != null ? f.getCategory().name() : null;
        r.caloriesPer100g    = f.getCaloriesPer100g();
        r.proteinPer100g     = f.getProteinPer100g();
        r.carbsPer100g       = f.getCarbsPer100g();
        r.fatPer100g         = f.getFatPer100g();
        r.fiberPer100g       = f.getFiberPer100g();
        r.servingDescription = f.getServingDescription();
        r.servingGrams       = f.getServingGrams();
        r.verified           = f.isVerified();
        return r;
    }

    public Long    getId()                  { return id; }
    public String  getName()               { return name; }
    public String  getCategory()           { return category; }
    public Double  getCaloriesPer100g()    { return caloriesPer100g; }
    public Double  getProteinPer100g()     { return proteinPer100g; }
    public Double  getCarbsPer100g()       { return carbsPer100g; }
    public Double  getFatPer100g()         { return fatPer100g; }
    public Double  getFiberPer100g()       { return fiberPer100g; }
    public String  getServingDescription() { return servingDescription; }
    public Double  getServingGrams()       { return servingGrams; }
    public boolean isVerified()            { return verified; }
}
