package com.nutriem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DietPlanRequest {

    @NotBlank(message = "Plan name is required")
    private String name;

    private String    description;

    @NotNull(message = "Patient ID is required")
    private Long      patientId;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer targetCalories;
    private Integer targetProteinG;
    private Integer targetCarbsG;
    private Integer targetFatG;
    private Integer targetFiberG;

    // "MANUAL" or "AI_GENERATED"
    private String generationSource;
    private String aiPrompt;
    private String aiNotes;

    public DietPlanRequest() {}

    public String getName()                     { return name; }
    public void setName(String v)               { this.name = v; }
    public String getDescription()              { return description; }
    public void setDescription(String v)        { this.description = v; }
    public Long getPatientId()                  { return patientId; }
    public void setPatientId(Long v)            { this.patientId = v; }
    public LocalDate getStartDate()             { return startDate; }
    public void setStartDate(LocalDate v)       { this.startDate = v; }
    public LocalDate getEndDate()               { return endDate; }
    public void setEndDate(LocalDate v)         { this.endDate = v; }
    public Integer getTargetCalories()          { return targetCalories; }
    public void setTargetCalories(Integer v)    { this.targetCalories = v; }
    public Integer getTargetProteinG()          { return targetProteinG; }
    public void setTargetProteinG(Integer v)    { this.targetProteinG = v; }
    public Integer getTargetCarbsG()            { return targetCarbsG; }
    public void setTargetCarbsG(Integer v)      { this.targetCarbsG = v; }
    public Integer getTargetFatG()              { return targetFatG; }
    public void setTargetFatG(Integer v)        { this.targetFatG = v; }
    public Integer getTargetFiberG()            { return targetFiberG; }
    public void setTargetFiberG(Integer v)      { this.targetFiberG = v; }
    public String getGenerationSource()         { return generationSource; }
    public void setGenerationSource(String v)   { this.generationSource = v; }
    public String getAiPrompt()                 { return aiPrompt; }
    public void setAiPrompt(String v)           { this.aiPrompt = v; }
    public String getAiNotes()                  { return aiNotes; }
    public void setAiNotes(String v)            { this.aiNotes = v; }
}
