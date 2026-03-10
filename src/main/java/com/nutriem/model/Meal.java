package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meals")
public class Meal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_plan_id", nullable = false)
    private DietPlan dietPlan;

    @Column(nullable = false) private String name;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private MealType mealType;

    private Integer dayOfWeek;

    @Column(columnDefinition = "TEXT") private String instructions;
    @Column(columnDefinition = "TEXT") private String notes;

    private Integer totalCalories;
    private Double totalProteinG, totalCarbsG, totalFatG, totalFiberG;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MealFood> mealFoods = new ArrayList<>();

    @CreationTimestamp @Column(updatable = false) private LocalDateTime createdAt;

    public Meal() {}

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public DietPlan getDietPlan()               { return dietPlan; }
    public void setDietPlan(DietPlan v)         { this.dietPlan = v; }
    public String getName()                     { return name; }
    public void setName(String v)               { this.name = v; }
    public MealType getMealType()               { return mealType; }
    public void setMealType(MealType v)         { this.mealType = v; }
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
    public List<MealFood> getMealFoods()        { return mealFoods; }
    public void setMealFoods(List<MealFood> v)  { this.mealFoods = v; }
    public LocalDateTime getCreatedAt()         { return createdAt; }

    public enum MealType {
        BREAKFAST, MORNING_SNACK, LUNCH, AFTERNOON_SNACK, DINNER, EVENING_SNACK
    }
}
