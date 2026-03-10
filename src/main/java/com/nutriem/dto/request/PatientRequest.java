package com.nutriem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PatientRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Must be a valid email")
    private String email;

    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    private Double weightKg;
    private Double heightCm;
    private String medicalConditions;
    private String allergies;
    private String foodPreferences;
    private String foodRestrictions;
    private String primaryGoal;
    private String activityLevel;
    private String notes;

    public PatientRequest() {}

    public String getFirstName()                { return firstName; }
    public void setFirstName(String v)          { this.firstName = v; }
    public String getLastName()                 { return lastName; }
    public void setLastName(String v)           { this.lastName = v; }
    public String getEmail()                    { return email; }
    public void setEmail(String v)              { this.email = v; }
    public String getPhoneNumber()              { return phoneNumber; }
    public void setPhoneNumber(String v)        { this.phoneNumber = v; }
    public LocalDate getDateOfBirth()           { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)     { this.dateOfBirth = v; }
    public String getGender()                   { return gender; }
    public void setGender(String v)             { this.gender = v; }
    public Double getWeightKg()                 { return weightKg; }
    public void setWeightKg(Double v)           { this.weightKg = v; }
    public Double getHeightCm()                 { return heightCm; }
    public void setHeightCm(Double v)           { this.heightCm = v; }
    public String getMedicalConditions()        { return medicalConditions; }
    public void setMedicalConditions(String v)  { this.medicalConditions = v; }
    public String getAllergies()                 { return allergies; }
    public void setAllergies(String v)          { this.allergies = v; }
    public String getFoodPreferences()          { return foodPreferences; }
    public void setFoodPreferences(String v)    { this.foodPreferences = v; }
    public String getFoodRestrictions()         { return foodRestrictions; }
    public void setFoodRestrictions(String v)   { this.foodRestrictions = v; }
    public String getPrimaryGoal()              { return primaryGoal; }
    public void setPrimaryGoal(String v)        { this.primaryGoal = v; }
    public String getActivityLevel()            { return activityLevel; }
    public void setActivityLevel(String v)      { this.activityLevel = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
}
