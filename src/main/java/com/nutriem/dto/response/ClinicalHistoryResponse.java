package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.ClinicalHistory;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicalHistoryResponse {
    private Long    id;
    private Long    patientId;
    private String  chronicDiseases;
    private String  familyHistory;
    private String  surgicalHistory;
    private String  currentMedications;
    private Integer mealsPerDay;
    private Boolean skipsBreakfast;
    private Boolean eatsLateNight;
    private String  foodAllergies;
    private String  foodIntolerances;
    private String  foodAversions;
    private String  foodPreferences;
    private String  dietaryRestrictions;
    private Boolean consumesAlcohol;
    private String  alcoholFrequency;
    private Boolean consumesCaffeine;
    private Integer waterIntakeLiters;
    private Boolean hasBloating;
    private Boolean hasConstipation;
    private Boolean hasDiarrhea;
    private Boolean hasAcidReflux;
    private Boolean hasIrritable;
    private String  digestiveNotes;
    private String  sleepHoursPerNight;
    private String  stressLevel;
    private Boolean smoker;
    private String  physicalActivityDetail;
    private String  occupation;
    private Double  fastingGlucose;
    private Double  hba1c;
    private Double  totalCholesterol;
    private Double  ldlCholesterol;
    private Double  hdlCholesterol;
    private Double  triglycerides;
    private Double  hemoglobin;
    private Double  ferritin;
    private Double  vitaminD;
    private Double  vitaminB12;
    private String  otherLabResults;
    private String  weightGoalDetail;
    private String  motivations;
    private String  previousDietAttempts;
    private String  nutritionistNotes;
    private LocalDateTime updatedAt;

    public static ClinicalHistoryResponse from(ClinicalHistory h) {
        ClinicalHistoryResponse r = new ClinicalHistoryResponse();
        r.id                   = h.getId();
        r.patientId            = h.getPatient().getId();
        r.chronicDiseases      = h.getChronicDiseases();
        r.familyHistory        = h.getFamilyHistory();
        r.surgicalHistory      = h.getSurgicalHistory();
        r.currentMedications   = h.getCurrentMedications();
        r.mealsPerDay          = h.getMealsPerDay();
        r.skipsBreakfast       = h.getSkipsBreakfast();
        r.eatsLateNight        = h.getEatsLateNight();
        r.foodAllergies        = h.getFoodAllergies();
        r.foodIntolerances     = h.getFoodIntolerances();
        r.foodAversions        = h.getFoodAversions();
        r.foodPreferences      = h.getFoodPreferences();
        r.dietaryRestrictions  = h.getDietaryRestrictions();
        r.consumesAlcohol      = h.getConsumesAlcohol();
        r.alcoholFrequency     = h.getAlcoholFrequency();
        r.consumesCaffeine     = h.getConsumesCaffeine();
        r.waterIntakeLiters    = h.getWaterIntakeLiters();
        r.hasBloating          = h.getHasBloating();
        r.hasConstipation      = h.getHasConstipation();
        r.hasDiarrhea          = h.getHasDiarrhea();
        r.hasAcidReflux        = h.getHasAcidReflux();
        r.hasIrritable         = h.getHasIrritable();
        r.digestiveNotes       = h.getDigestiveNotes();
        r.sleepHoursPerNight   = h.getSleepHoursPerNight();
        r.stressLevel          = h.getStressLevel();
        r.smoker               = h.getSmoker();
        r.physicalActivityDetail = h.getPhysicalActivityDetail();
        r.occupation           = h.getOccupation();
        r.fastingGlucose       = h.getFastingGlucose();
        r.hba1c                = h.getHba1c();
        r.totalCholesterol     = h.getTotalCholesterol();
        r.ldlCholesterol       = h.getLdlCholesterol();
        r.hdlCholesterol       = h.getHdlCholesterol();
        r.triglycerides        = h.getTriglycerides();
        r.hemoglobin           = h.getHemoglobin();
        r.ferritin             = h.getFerritin();
        r.vitaminD             = h.getVitaminD();
        r.vitaminB12           = h.getVitaminB12();
        r.otherLabResults      = h.getOtherLabResults();
        r.weightGoalDetail     = h.getWeightGoalDetail();
        r.motivations          = h.getMotivations();
        r.previousDietAttempts = h.getPreviousDietAttempts();
        r.nutritionistNotes    = h.getNutritionistNotes();
        r.updatedAt            = h.getUpdatedAt();
        return r;
    }

    public Long    getId()                      { return id; }
    public Long    getPatientId()               { return patientId; }
    public String  getChronicDiseases()         { return chronicDiseases; }
    public String  getFamilyHistory()           { return familyHistory; }
    public String  getSurgicalHistory()         { return surgicalHistory; }
    public String  getCurrentMedications()      { return currentMedications; }
    public Integer getMealsPerDay()             { return mealsPerDay; }
    public Boolean getSkipsBreakfast()          { return skipsBreakfast; }
    public Boolean getEatsLateNight()           { return eatsLateNight; }
    public String  getFoodAllergies()           { return foodAllergies; }
    public String  getFoodIntolerances()        { return foodIntolerances; }
    public String  getFoodAversions()           { return foodAversions; }
    public String  getFoodPreferences()         { return foodPreferences; }
    public String  getDietaryRestrictions()     { return dietaryRestrictions; }
    public Boolean getConsumesAlcohol()         { return consumesAlcohol; }
    public String  getAlcoholFrequency()        { return alcoholFrequency; }
    public Boolean getConsumesCaffeine()        { return consumesCaffeine; }
    public Integer getWaterIntakeLiters()       { return waterIntakeLiters; }
    public Boolean getHasBloating()             { return hasBloating; }
    public Boolean getHasConstipation()         { return hasConstipation; }
    public Boolean getHasDiarrhea()             { return hasDiarrhea; }
    public Boolean getHasAcidReflux()           { return hasAcidReflux; }
    public Boolean getHasIrritable()            { return hasIrritable; }
    public String  getDigestiveNotes()          { return digestiveNotes; }
    public String  getSleepHoursPerNight()      { return sleepHoursPerNight; }
    public String  getStressLevel()             { return stressLevel; }
    public Boolean getSmoker()                  { return smoker; }
    public String  getPhysicalActivityDetail()  { return physicalActivityDetail; }
    public String  getOccupation()              { return occupation; }
    public Double  getFastingGlucose()          { return fastingGlucose; }
    public Double  getHba1c()                   { return hba1c; }
    public Double  getTotalCholesterol()        { return totalCholesterol; }
    public Double  getLdlCholesterol()          { return ldlCholesterol; }
    public Double  getHdlCholesterol()          { return hdlCholesterol; }
    public Double  getTriglycerides()           { return triglycerides; }
    public Double  getHemoglobin()              { return hemoglobin; }
    public Double  getFerritin()                { return ferritin; }
    public Double  getVitaminD()                { return vitaminD; }
    public Double  getVitaminB12()              { return vitaminB12; }
    public String  getOtherLabResults()         { return otherLabResults; }
    public String  getWeightGoalDetail()        { return weightGoalDetail; }
    public String  getMotivations()             { return motivations; }
    public String  getPreviousDietAttempts()    { return previousDietAttempts; }
    public String  getNutritionistNotes()       { return nutritionistNotes; }
    public LocalDateTime getUpdatedAt()         { return updatedAt; }
}
