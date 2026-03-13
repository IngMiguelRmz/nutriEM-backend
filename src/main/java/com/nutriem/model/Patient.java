package com.nutriem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(nullable = false) private String firstName;
    @NotBlank @Column(nullable = false) private String lastName;
    @Email private String email;
    private String phoneNumber;

    @Column(nullable = false) private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Gender gender;

    private Double weightKg;
    private Double heightCm;

    @Column(columnDefinition = "TEXT") private String medicalConditions;
    @Column(columnDefinition = "TEXT") private String allergies;
    @Column(columnDefinition = "TEXT") private String foodPreferences;
    @Column(columnDefinition = "TEXT") private String foodRestrictions;

    @Enumerated(EnumType.STRING) private Goal primaryGoal;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel = ActivityLevel.SEDENTARY;

    @Column(columnDefinition = "TEXT") private String notes;
    private String portalPassword;  // BCrypt-hashed, null = portal disabled

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutriologist_id", nullable = false)
    private User nutriologist;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DietPlan> dietPlans = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PatientMeasurement> measurements = new ArrayList<>();

    @CreationTimestamp @Column(updatable = false) private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;

    public Patient() {}

    // ── Builder ───────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String firstName, lastName, email, phoneNumber;
        private LocalDate dateOfBirth;
        private Gender gender;
        private Double weightKg, heightCm;
        private String medicalConditions, allergies, foodPreferences, foodRestrictions, notes;
        private Goal primaryGoal;
        private ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        private Status status = Status.ACTIVE;
        private User nutriologist;

        public Builder id(Long v)                       { this.id = v; return this; }
        public Builder firstName(String v)              { this.firstName = v; return this; }
        public Builder lastName(String v)               { this.lastName = v; return this; }
        public Builder email(String v)                  { this.email = v; return this; }
        public Builder phoneNumber(String v)            { this.phoneNumber = v; return this; }
        public Builder dateOfBirth(LocalDate v)         { this.dateOfBirth = v; return this; }
        public Builder gender(Gender v)                 { this.gender = v; return this; }
        public Builder weightKg(Double v)               { this.weightKg = v; return this; }
        public Builder heightCm(Double v)               { this.heightCm = v; return this; }
        public Builder medicalConditions(String v)      { this.medicalConditions = v; return this; }
        public Builder allergies(String v)              { this.allergies = v; return this; }
        public Builder foodPreferences(String v)        { this.foodPreferences = v; return this; }
        public Builder foodRestrictions(String v)       { this.foodRestrictions = v; return this; }
        public Builder notes(String v)                  { this.notes = v; return this; }
        public Builder primaryGoal(Goal v)              { this.primaryGoal = v; return this; }
        public Builder activityLevel(ActivityLevel v)   { this.activityLevel = v; return this; }
        public Builder status(Status v)                 { this.status = v; return this; }
        public Builder nutriologist(User v)             { this.nutriologist = v; return this; }

        public Patient build() {
            Patient p = new Patient();
            p.id = id; p.firstName = firstName; p.lastName = lastName;
            p.email = email; p.phoneNumber = phoneNumber;
            p.dateOfBirth = dateOfBirth; p.gender = gender;
            p.weightKg = weightKg; p.heightCm = heightCm;
            p.medicalConditions = medicalConditions; p.allergies = allergies;
            p.foodPreferences = foodPreferences; p.foodRestrictions = foodRestrictions;
            p.notes = notes; p.primaryGoal = primaryGoal;
            p.activityLevel = activityLevel; p.status = status;
            p.nutriologist = nutriologist;
            return p;
        }
    }

    // ── Computed ──────────────────────────────────────────────
    @Transient
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Transient
    public Double getBmi() {
        if (weightKg == null || heightCm == null || heightCm == 0) return null;
        double h = heightCm / 100.0;
        return Math.round((weightKg / (h * h)) * 10.0) / 10.0;
    }

    public String getFullName() { return firstName + " " + lastName; }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId()                             { return id; }
    public void setId(Long v)                       { this.id = v; }
    public String getFirstName()                    { return firstName; }
    public void setFirstName(String v)              { this.firstName = v; }
    public String getLastName()                     { return lastName; }
    public void setLastName(String v)               { this.lastName = v; }
    public String getEmail()                        { return email; }
    public void setEmail(String v)                  { this.email = v; }
    public String getPhoneNumber()                  { return phoneNumber; }
    public void setPhoneNumber(String v)            { this.phoneNumber = v; }
    public LocalDate getDateOfBirth()               { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v)         { this.dateOfBirth = v; }
    public Gender getGender()                       { return gender; }
    public void setGender(Gender v)                 { this.gender = v; }
    public Double getWeightKg()                     { return weightKg; }
    public void setWeightKg(Double v)               { this.weightKg = v; }
    public Double getHeightCm()                     { return heightCm; }
    public void setHeightCm(Double v)               { this.heightCm = v; }
    public String getMedicalConditions()            { return medicalConditions; }
    public void setMedicalConditions(String v)      { this.medicalConditions = v; }
    public String getAllergies()                     { return allergies; }
    public void setAllergies(String v)              { this.allergies = v; }
    public String getFoodPreferences()              { return foodPreferences; }
    public void setFoodPreferences(String v)        { this.foodPreferences = v; }
    public String getFoodRestrictions()             { return foodRestrictions; }
    public void setFoodRestrictions(String v)       { this.foodRestrictions = v; }
    public Goal getPrimaryGoal()                    { return primaryGoal; }
    public void setPrimaryGoal(Goal v)              { this.primaryGoal = v; }
    public ActivityLevel getActivityLevel()         { return activityLevel; }
    public void setActivityLevel(ActivityLevel v)   { this.activityLevel = v; }
    public String getNotes()                        { return notes; }
    public void setNotes(String v)                  { this.notes = v; }
    public String getPortalPassword()               { return portalPassword; }
    public void setPortalPassword(String v)         { this.portalPassword = v; }
    public Status getStatus()                       { return status; }
    public void setStatus(Status v)                 { this.status = v; }
    public User getNutritionistId()                 { return nutriologist; }
    public User getNutrologist()                    { return nutriologist; }
    public void setNutrologist(User v)              { this.nutriologist = v; }
    public List<DietPlan> getDietPlans()            { return dietPlans; }
    public void setDietPlans(List<DietPlan> v)      { this.dietPlans = v; }
    public List<PatientMeasurement> getMeasurements()           { return measurements; }
    public void setMeasurements(List<PatientMeasurement> v)     { this.measurements = v; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public LocalDateTime getUpdatedAt()             { return updatedAt; }

    public enum Gender        { MALE, FEMALE, OTHER }
    public enum Goal          { WEIGHT_LOSS, WEIGHT_GAIN, MUSCLE_GAIN, CHOLESTEROL_CONTROL,
                                BLOOD_SUGAR_MANAGEMENT, GENERAL_HEALTH, SPORTS_PERFORMANCE }
    public enum ActivityLevel { SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTRA_ACTIVE }
    public enum Status        { ACTIVE, INACTIVE, DISCHARGED }
}
