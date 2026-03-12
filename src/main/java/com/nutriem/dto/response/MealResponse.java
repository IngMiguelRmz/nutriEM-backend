package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MealResponse {

    private Long                   id;
    private String                 name;
    private String                 mealType;
    private Integer                dayOfWeek;
    private String                 instructions;
    private String                 notes;
    private Integer                totalCalories;
    private Double                 totalProteinG;
    private Double                 totalCarbsG;
    private Double                 totalFatG;
    private Double                 totalFiberG;
    private List<MealFoodResponse> ingredients;
    private LocalDateTime          createdAt;

    public MealResponse() {}

    public static MealResponse from(Meal m) {
        MealResponse r = new MealResponse();
        r.id            = m.getId();
        r.name          = m.getName();
        r.mealType      = m.getMealType() != null ? m.getMealType().name() : null;
        r.dayOfWeek     = m.getDayOfWeek();
        r.instructions  = m.getInstructions();
        r.notes         = m.getNotes();
        r.totalCalories = m.getTotalCalories();
        r.totalProteinG = m.getTotalProteinG();
        r.totalCarbsG   = m.getTotalCarbsG();
        r.totalFatG     = m.getTotalFatG();
        r.totalFiberG   = m.getTotalFiberG();
        r.createdAt     = m.getCreatedAt();

        if (m.getMealFoods() != null && !m.getMealFoods().isEmpty()) {
            r.ingredients = m.getMealFoods().stream()
                    .map(MealFoodResponse::from)
                    .collect(Collectors.toList());
        }
        return r;
    }

    public Long                   getId()                       { return id; }
    public void                   setId(Long v)                 { this.id = v; }
    public String                 getName()                     { return name; }
    public void                   setName(String v)             { this.name = v; }
    public String                 getMealType()                 { return mealType; }
    public void                   setMealType(String v)         { this.mealType = v; }
    public Integer                getDayOfWeek()                { return dayOfWeek; }
    public void                   setDayOfWeek(Integer v)       { this.dayOfWeek = v; }
    public String                 getInstructions()             { return instructions; }
    public void                   setInstructions(String v)     { this.instructions = v; }
    public String                 getNotes()                    { return notes; }
    public void                   setNotes(String v)            { this.notes = v; }
    public Integer                getTotalCalories()            { return totalCalories; }
    public void                   setTotalCalories(Integer v)   { this.totalCalories = v; }
    public Double                 getTotalProteinG()            { return totalProteinG; }
    public void                   setTotalProteinG(Double v)    { this.totalProteinG = v; }
    public Double                 getTotalCarbsG()              { return totalCarbsG; }
    public void                   setTotalCarbsG(Double v)      { this.totalCarbsG = v; }
    public Double                 getTotalFatG()                { return totalFatG; }
    public void                   setTotalFatG(Double v)        { this.totalFatG = v; }
    public Double                 getTotalFiberG()              { return totalFiberG; }
    public void                   setTotalFiberG(Double v)      { this.totalFiberG = v; }
    public List<MealFoodResponse> getIngredients()              { return ingredients; }
    public void                   setIngredients(List<MealFoodResponse> v) { this.ingredients = v; }
    public LocalDateTime          getCreatedAt()                { return createdAt; }
    public void                   setCreatedAt(LocalDateTime v) { this.createdAt = v; }
}
