package com.nutriem.dto.request;

public class IngredientRequest {
    private Long   foodId;           // optional — link to Food table
    private String ingredientName;   // required if foodId is null
    private Double quantityGrams;
    private String servingDescription;
    private Double calories;
    private Double proteinG;
    private Double carbsG;
    private Double fatG;
    private Double fiberG;

    public Long   getFoodId()                    { return foodId; }
    public void   setFoodId(Long v)              { this.foodId = v; }
    public String getIngredientName()            { return ingredientName; }
    public void   setIngredientName(String v)    { this.ingredientName = v; }
    public Double getQuantityGrams()             { return quantityGrams; }
    public void   setQuantityGrams(Double v)     { this.quantityGrams = v; }
    public String getServingDescription()        { return servingDescription; }
    public void   setServingDescription(String v){ this.servingDescription = v; }
    public Double getCalories()                  { return calories; }
    public void   setCalories(Double v)          { this.calories = v; }
    public Double getProteinG()                  { return proteinG; }
    public void   setProteinG(Double v)          { this.proteinG = v; }
    public Double getCarbsG()                    { return carbsG; }
    public void   setCarbsG(Double v)            { this.carbsG = v; }
    public Double getFatG()                      { return fatG; }
    public void   setFatG(Double v)              { this.fatG = v; }
    public Double getFiberG()                    { return fiberG; }
    public void   setFiberG(Double v)            { this.fiberG = v; }
}
