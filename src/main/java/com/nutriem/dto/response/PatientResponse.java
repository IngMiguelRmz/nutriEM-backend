package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientResponse {

    private Long          id;
    private String        firstName;
    private String        lastName;
    private String        fullName;
    private String        email;
    private String        phoneNumber;
    private LocalDate     dateOfBirth;
    private Integer       age;
    private String        gender;
    private Double        weightKg;
    private Double        heightCm;
    private Double        bmi;
    private String        medicalConditions;
    private String        allergies;
    private String        foodPreferences;
    private String        foodRestrictions;
    private String        primaryGoal;
    private String        activityLevel;
    private String        notes;
    private String        status;
    private Integer       totalDietPlans;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PatientResponse() {}

    // ── Static factory from entity ────────────────────────────
    public static PatientResponse from(Patient p) {
        PatientResponse r = new PatientResponse();
        r.id               = p.getId();
        r.firstName        = p.getFirstName();
        r.lastName         = p.getLastName();
        r.fullName         = p.getFullName();
        r.email            = p.getEmail();
        r.phoneNumber      = p.getPhoneNumber();
        r.dateOfBirth      = p.getDateOfBirth();
        r.age              = p.getAge();
        r.gender           = p.getGender() != null ? p.getGender().name() : null;
        r.weightKg         = p.getWeightKg();
        r.heightCm         = p.getHeightCm();
        r.bmi              = p.getBmi();
        r.medicalConditions = p.getMedicalConditions();
        r.allergies        = p.getAllergies();
        r.foodPreferences  = p.getFoodPreferences();
        r.foodRestrictions = p.getFoodRestrictions();
        r.primaryGoal      = p.getPrimaryGoal() != null ? p.getPrimaryGoal().name() : null;
        r.activityLevel    = p.getActivityLevel() != null ? p.getActivityLevel().name() : null;
        r.notes            = p.getNotes();
        r.status           = p.getStatus() != null ? p.getStatus().name() : null;
        r.createdAt        = p.getCreatedAt();
        r.updatedAt        = p.getUpdatedAt();
        return r;
    }

    public static PatientResponse summary(Patient p) {
        PatientResponse r = from(p);
        r.totalDietPlans = p.getDietPlans() != null ? p.getDietPlans().size() : 0;
        return r;
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId()                             { return id; }
    public void setId(Long v)                       { this.id = v; }
    public String getFirstName()                    { return firstName; }
    public void setFirstName(String v)              { this.firstName = v; }
    public String getLastName()                     { return lastName; }
    public void setLastName(String v)               { this.lastName = v; }
    public String getFullName()                     { return fullName; }
    public void setFullName(String v)               { this.fullName = v; }
    public String getEmail()                        { return email; }
    public void setEmail(String v)                  { this.email = v; }
    public String getPhoneNumber()                  { return phoneNumber; }
    public void setPhoneNumber(String v)            { this.phoneNumber = v; }
    public LocalDate getDateOfBirth()               { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)         { this.dateOfBirth = v; }
    public Integer getAge()                         { return age; }
    public void setAge(Integer v)                   { this.age = v; }
    public String getGender()                       { return gender; }
    public void setGender(String v)                 { this.gender = v; }
    public Double getWeightKg()                     { return weightKg; }
    public void setWeightKg(Double v)               { this.weightKg = v; }
    public Double getHeightCm()                     { return heightCm; }
    public void setHeightCm(Double v)               { this.heightCm = v; }
    public Double getBmi()                          { return bmi; }
    public void setBmi(Double v)                    { this.bmi = v; }
    public String getMedicalConditions()            { return medicalConditions; }
    public void setMedicalConditions(String v)      { this.medicalConditions = v; }
    public String getAllergies()                     { return allergies; }
    public void setAllergies(String v)              { this.allergies = v; }
    public String getFoodPreferences()              { return foodPreferences; }
    public void setFoodPreferences(String v)        { this.foodPreferences = v; }
    public String getFoodRestrictions()             { return foodRestrictions; }
    public void setFoodRestrictions(String v)       { this.foodRestrictions = v; }
    public String getPrimaryGoal()                  { return primaryGoal; }
    public void setPrimaryGoal(String v)            { this.primaryGoal = v; }
    public String getActivityLevel()                { return activityLevel; }
    public void setActivityLevel(String v)          { this.activityLevel = v; }
    public String getNotes()                        { return notes; }
    public void setNotes(String v)                  { this.notes = v; }
    public String getStatus()                       { return status; }
    public void setStatus(String v)                 { this.status = v; }
    public Integer getTotalDietPlans()              { return totalDietPlans; }
    public void setTotalDietPlans(Integer v)        { this.totalDietPlans = v; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public void setCreatedAt(LocalDateTime v)       { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()             { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v)       { this.updatedAt = v; }
}
