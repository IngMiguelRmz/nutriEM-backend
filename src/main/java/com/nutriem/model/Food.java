package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "foods", indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "category")
})
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String nameEs;
    private String brand;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Double caloriesPer100g;
    private Double proteinPer100g;
    private Double carbsPer100g;
    private Double fatPer100g;
    private Double fiberPer100g;
    private Double sugarPer100g;
    private Double sodiumMgPer100g;

    private String servingDescription;
    private Double servingGrams;
    private String usdaFoodId;
    private boolean isVerified = false;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Food() {}

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public String getName()                     { return name; }
    public void setName(String v)               { this.name = v; }
    public String getNameEs()                   { return nameEs; }
    public void setNameEs(String v)             { this.nameEs = v; }
    public String getBrand()                    { return brand; }
    public void setBrand(String v)              { this.brand = v; }
    public Category getCategory()               { return category; }
    public void setCategory(Category v)         { this.category = v; }
    public Double getCaloriesPer100g()          { return caloriesPer100g; }
    public void setCaloriesPer100g(Double v)    { this.caloriesPer100g = v; }
    public Double getProteinPer100g()           { return proteinPer100g; }
    public void setProteinPer100g(Double v)     { this.proteinPer100g = v; }
    public Double getCarbsPer100g()             { return carbsPer100g; }
    public void setCarbsPer100g(Double v)       { this.carbsPer100g = v; }
    public Double getFatPer100g()               { return fatPer100g; }
    public void setFatPer100g(Double v)         { this.fatPer100g = v; }
    public Double getFiberPer100g()             { return fiberPer100g; }
    public void setFiberPer100g(Double v)       { this.fiberPer100g = v; }
    public Double getSugarPer100g()             { return sugarPer100g; }
    public void setSugarPer100g(Double v)       { this.sugarPer100g = v; }
    public Double getSodiumMgPer100g()          { return sodiumMgPer100g; }
    public void setSodiumMgPer100g(Double v)    { this.sodiumMgPer100g = v; }
    public String getServingDescription()       { return servingDescription; }
    public void setServingDescription(String v) { this.servingDescription = v; }
    public Double getServingGrams()             { return servingGrams; }
    public void setServingGrams(Double v)       { this.servingGrams = v; }
    public String getUsdaFoodId()               { return usdaFoodId; }
    public void setUsdaFoodId(String v)         { this.usdaFoodId = v; }
    public boolean isVerified()                 { return isVerified; }
    public void setVerified(boolean v)          { this.isVerified = v; }
    public LocalDateTime getCreatedAt()         { return createdAt; }

    public enum Category {
        GRAINS, VEGETABLES, FRUITS, DAIRY, MEAT, FISH_SEAFOOD,
        LEGUMES, NUTS_SEEDS, OILS_FATS, SWEETS, BEVERAGES,
        PROCESSED, EGGS, OTHER
    }
}
