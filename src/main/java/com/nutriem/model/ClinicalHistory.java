package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "clinical_histories")
public class ClinicalHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private Patient patient;

    // ── Personal & Family History ─────────────────────────────
    @Column(columnDefinition = "TEXT") private String chronicDiseases;       // diabetes, hypertension, etc.
    @Column(columnDefinition = "TEXT") private String familyHistory;          // hereditary conditions
    @Column(columnDefinition = "TEXT") private String surgicalHistory;        // past surgeries
    @Column(columnDefinition = "TEXT") private String currentMedications;     // medications + supplements

    // ── Eating Habits ─────────────────────────────────────────
    private Integer mealsPerDay;
    private Boolean skipsBreakfast;
    private Boolean eatsLateNight;
    @Column(columnDefinition = "TEXT") private String foodAllergies;          // detailed allergies
    @Column(columnDefinition = "TEXT") private String foodIntolerances;       // lactose, gluten, etc.
    @Column(columnDefinition = "TEXT") private String foodAversions;          // dislikes
    @Column(columnDefinition = "TEXT") private String foodPreferences;        // favorites
    @Column(columnDefinition = "TEXT") private String dietaryRestrictions;    // vegan, halal, kosher, etc.
    private Boolean consumesAlcohol;
    private String  alcoholFrequency;                                          // daily/weekly/occasional
    private Boolean consumesCaffeine;
    private Integer waterIntakeLiters;                                        // daily water in liters*10

    // ── Digestive Health ──────────────────────────────────────
    private Boolean hasBloating;
    private Boolean hasConstipation;
    private Boolean hasDiarrhea;
    private Boolean hasAcidReflux;
    private Boolean hasIrritable;                                              // IBS
    @Column(columnDefinition = "TEXT") private String digestiveNotes;

    // ── Lifestyle ─────────────────────────────────────────────
    private String  sleepHoursPerNight;                                       // e.g. "6-7"
    private String  stressLevel;                                               // LOW / MODERATE / HIGH / VERY_HIGH
    private Boolean smoker;
    private String  physicalActivityDetail;                                    // type, frequency, duration
    private String  occupation;                                                // sedentary/active job context

    // ── Lab Results (latest) ──────────────────────────────────
    private Double  fastingGlucose;                                            // mg/dL
    private Double  hba1c;                                                     // %
    private Double  totalCholesterol;                                          // mg/dL
    private Double  ldlCholesterol;
    private Double  hdlCholesterol;
    private Double  triglycerides;
    private Double  hemoglobin;
    private Double  ferritin;
    private Double  vitaminD;
    private Double  vitaminB12;
    @Column(columnDefinition = "TEXT") private String otherLabResults;        // free text for other values

    // ── Nutrition Goals (detailed) ────────────────────────────
    @Column(columnDefinition = "TEXT") private String weightGoalDetail;       // specific target + timeline
    @Column(columnDefinition = "TEXT") private String motivations;            // why they want to change
    @Column(columnDefinition = "TEXT") private String previousDietAttempts;   // what they've tried before
    @Column(columnDefinition = "TEXT") private String nutritionistNotes;      // free-form clinical notes

    @UpdateTimestamp private LocalDateTime updatedAt;

    public ClinicalHistory() {}

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId()                                     { return id; }
    public Patient getPatient()                             { return patient; }
    public void setPatient(Patient v)                       { this.patient = v; }

    public String getChronicDiseases()                      { return chronicDiseases; }
    public void setChronicDiseases(String v)                { this.chronicDiseases = v; }
    public String getFamilyHistory()                        { return familyHistory; }
    public void setFamilyHistory(String v)                  { this.familyHistory = v; }
    public String getSurgicalHistory()                      { return surgicalHistory; }
    public void setSurgicalHistory(String v)                { this.surgicalHistory = v; }
    public String getCurrentMedications()                   { return currentMedications; }
    public void setCurrentMedications(String v)             { this.currentMedications = v; }

    public Integer getMealsPerDay()                         { return mealsPerDay; }
    public void setMealsPerDay(Integer v)                   { this.mealsPerDay = v; }
    public Boolean getSkipsBreakfast()                      { return skipsBreakfast; }
    public void setSkipsBreakfast(Boolean v)                { this.skipsBreakfast = v; }
    public Boolean getEatsLateNight()                       { return eatsLateNight; }
    public void setEatsLateNight(Boolean v)                 { this.eatsLateNight = v; }
    public String getFoodAllergies()                        { return foodAllergies; }
    public void setFoodAllergies(String v)                  { this.foodAllergies = v; }
    public String getFoodIntolerances()                     { return foodIntolerances; }
    public void setFoodIntolerances(String v)               { this.foodIntolerances = v; }
    public String getFoodAversions()                        { return foodAversions; }
    public void setFoodAversions(String v)                  { this.foodAversions = v; }
    public String getFoodPreferences()                      { return foodPreferences; }
    public void setFoodPreferences(String v)                { this.foodPreferences = v; }
    public String getDietaryRestrictions()                  { return dietaryRestrictions; }
    public void setDietaryRestrictions(String v)            { this.dietaryRestrictions = v; }
    public Boolean getConsumesAlcohol()                     { return consumesAlcohol; }
    public void setConsumesAlcohol(Boolean v)               { this.consumesAlcohol = v; }
    public String getAlcoholFrequency()                     { return alcoholFrequency; }
    public void setAlcoholFrequency(String v)               { this.alcoholFrequency = v; }
    public Boolean getConsumesCaffeine()                    { return consumesCaffeine; }
    public void setConsumesCaffeine(Boolean v)              { this.consumesCaffeine = v; }
    public Integer getWaterIntakeLiters()                   { return waterIntakeLiters; }
    public void setWaterIntakeLiters(Integer v)             { this.waterIntakeLiters = v; }

    public Boolean getHasBloating()                         { return hasBloating; }
    public void setHasBloating(Boolean v)                   { this.hasBloating = v; }
    public Boolean getHasConstipation()                     { return hasConstipation; }
    public void setHasConstipation(Boolean v)               { this.hasConstipation = v; }
    public Boolean getHasDiarrhea()                         { return hasDiarrhea; }
    public void setHasDiarrhea(Boolean v)                   { this.hasDiarrhea = v; }
    public Boolean getHasAcidReflux()                       { return hasAcidReflux; }
    public void setHasAcidReflux(Boolean v)                 { this.hasAcidReflux = v; }
    public Boolean getHasIrritable()                        { return hasIrritable; }
    public void setHasIrritable(Boolean v)                  { this.hasIrritable = v; }
    public String getDigestiveNotes()                       { return digestiveNotes; }
    public void setDigestiveNotes(String v)                 { this.digestiveNotes = v; }

    public String getSleepHoursPerNight()                   { return sleepHoursPerNight; }
    public void setSleepHoursPerNight(String v)             { this.sleepHoursPerNight = v; }
    public String getStressLevel()                          { return stressLevel; }
    public void setStressLevel(String v)                    { this.stressLevel = v; }
    public Boolean getSmoker()                              { return smoker; }
    public void setSmoker(Boolean v)                        { this.smoker = v; }
    public String getPhysicalActivityDetail()               { return physicalActivityDetail; }
    public void setPhysicalActivityDetail(String v)         { this.physicalActivityDetail = v; }
    public String getOccupation()                           { return occupation; }
    public void setOccupation(String v)                     { this.occupation = v; }

    public Double getFastingGlucose()                       { return fastingGlucose; }
    public void setFastingGlucose(Double v)                 { this.fastingGlucose = v; }
    public Double getHba1c()                                { return hba1c; }
    public void setHba1c(Double v)                          { this.hba1c = v; }
    public Double getTotalCholesterol()                     { return totalCholesterol; }
    public void setTotalCholesterol(Double v)               { this.totalCholesterol = v; }
    public Double getLdlCholesterol()                       { return ldlCholesterol; }
    public void setLdlCholesterol(Double v)                 { this.ldlCholesterol = v; }
    public Double getHdlCholesterol()                       { return hdlCholesterol; }
    public void setHdlCholesterol(Double v)                 { this.hdlCholesterol = v; }
    public Double getTriglycerides()                        { return triglycerides; }
    public void setTriglycerides(Double v)                  { this.triglycerides = v; }
    public Double getHemoglobin()                           { return hemoglobin; }
    public void setHemoglobin(Double v)                     { this.hemoglobin = v; }
    public Double getFerritin()                             { return ferritin; }
    public void setFerritin(Double v)                       { this.ferritin = v; }
    public Double getVitaminD()                             { return vitaminD; }
    public void setVitaminD(Double v)                       { this.vitaminD = v; }
    public Double getVitaminB12()                           { return vitaminB12; }
    public void setVitaminB12(Double v)                     { this.vitaminB12 = v; }
    public String getOtherLabResults()                      { return otherLabResults; }
    public void setOtherLabResults(String v)                { this.otherLabResults = v; }

    public String getWeightGoalDetail()                     { return weightGoalDetail; }
    public void setWeightGoalDetail(String v)               { this.weightGoalDetail = v; }
    public String getMotivations()                          { return motivations; }
    public void setMotivations(String v)                    { this.motivations = v; }
    public String getPreviousDietAttempts()                 { return previousDietAttempts; }
    public void setPreviousDietAttempts(String v)           { this.previousDietAttempts = v; }
    public String getNutritionistNotes()                    { return nutritionistNotes; }
    public void setNutritionistNotes(String v)              { this.nutritionistNotes = v; }
    public LocalDateTime getUpdatedAt()                     { return updatedAt; }
}
