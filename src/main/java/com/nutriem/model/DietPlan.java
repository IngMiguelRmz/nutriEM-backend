package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_plans")
public class DietPlan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String name;
    @Column(columnDefinition = "TEXT") private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    private LocalDate startDate, endDate;
    private Integer targetCalories, targetProteinG, targetCarbsG, targetFatG, targetFiberG;

    @Enumerated(EnumType.STRING) private Status status = Status.ACTIVE;
    @Enumerated(EnumType.STRING) private GenerationSource generationSource = GenerationSource.MANUAL;

    @Column(columnDefinition = "TEXT") private String aiPrompt;
    @Column(columnDefinition = "TEXT") private String aiNotes;

    @OneToMany(mappedBy = "dietPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals = new ArrayList<>();

    @CreationTimestamp @Column(updatable = false) private LocalDateTime createdAt;
    @UpdateTimestamp private LocalDateTime updatedAt;

    public DietPlan() {}

    public Long getId()                             { return id; }
    public void setId(Long v)                       { this.id = v; }
    public String getName()                         { return name; }
    public void setName(String v)                   { this.name = v; }
    public String getDescription()                  { return description; }
    public void setDescription(String v)            { this.description = v; }
    public Patient getPatient()                     { return patient; }
    public void setPatient(Patient v)               { this.patient = v; }
    public User getCreatedBy()                      { return createdBy; }
    public void setCreatedBy(User v)                { this.createdBy = v; }
    public LocalDate getStartDate()                 { return startDate; }
    public void setStartDate(LocalDate v)           { this.startDate = v; }
    public LocalDate getEndDate()                   { return endDate; }
    public void setEndDate(LocalDate v)             { this.endDate = v; }
    public Integer getTargetCalories()              { return targetCalories; }
    public void setTargetCalories(Integer v)        { this.targetCalories = v; }
    public Integer getTargetProteinG()              { return targetProteinG; }
    public void setTargetProteinG(Integer v)        { this.targetProteinG = v; }
    public Integer getTargetCarbsG()                { return targetCarbsG; }
    public void setTargetCarbsG(Integer v)          { this.targetCarbsG = v; }
    public Integer getTargetFatG()                  { return targetFatG; }
    public void setTargetFatG(Integer v)            { this.targetFatG = v; }
    public Integer getTargetFiberG()                { return targetFiberG; }
    public void setTargetFiberG(Integer v)          { this.targetFiberG = v; }
    public Status getStatus()                       { return status; }
    public void setStatus(Status v)                 { this.status = v; }
    public GenerationSource getGenerationSource()   { return generationSource; }
    public void setGenerationSource(GenerationSource v) { this.generationSource = v; }
    public String getAiPrompt()                     { return aiPrompt; }
    public void setAiPrompt(String v)               { this.aiPrompt = v; }
    public String getAiNotes()                      { return aiNotes; }
    public void setAiNotes(String v)                { this.aiNotes = v; }
    public List<Meal> getMeals()                    { return meals; }
    public void setMeals(List<Meal> v)              { this.meals = v; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public LocalDateTime getUpdatedAt()             { return updatedAt; }

    public enum Status           { ACTIVE, COMPLETED, PAUSED, ARCHIVED }
    public enum GenerationSource { MANUAL, AI_GENERATED }
}
