package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.DietPlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DietPlanResponse {

    private Long          id;
    private String        name;
    private String        description;
    private Long          patientId;
    private String        patientName;
    private LocalDate     startDate;
    private LocalDate     endDate;
    private Integer       targetCalories;
    private Integer       targetProteinG;
    private Integer       targetCarbsG;
    private Integer       targetFatG;
    private Integer       targetFiberG;
    private String        status;
    private String        generationSource;
    private String        aiPrompt;
    private String        aiNotes;
    private List<MealResponse> meals;
    private Integer       totalMeals;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DietPlanResponse() {}

    public static DietPlanResponse from(DietPlan d) {
        DietPlanResponse r = new DietPlanResponse();
        r.id               = d.getId();
        r.name             = d.getName();
        r.description      = d.getDescription();
        r.patientId        = d.getPatient() != null ? d.getPatient().getId() : null;
        r.patientName      = d.getPatient() != null ? d.getPatient().getFullName() : null;
        r.startDate        = d.getStartDate();
        r.endDate          = d.getEndDate();
        r.targetCalories   = d.getTargetCalories();
        r.targetProteinG   = d.getTargetProteinG();
        r.targetCarbsG     = d.getTargetCarbsG();
        r.targetFatG       = d.getTargetFatG();
        r.targetFiberG     = d.getTargetFiberG();
        r.status           = d.getStatus() != null ? d.getStatus().name() : null;
        r.generationSource = d.getGenerationSource() != null ? d.getGenerationSource().name() : null;
        r.aiPrompt         = d.getAiPrompt();
        r.aiNotes          = d.getAiNotes();
        r.createdAt        = d.getCreatedAt();
        r.updatedAt        = d.getUpdatedAt();
        return r;
    }

    public static DietPlanResponse withMeals(DietPlan d) {
        DietPlanResponse r = from(d);
        if (d.getMeals() != null) {
            r.meals      = d.getMeals().stream().map(MealResponse::from).collect(Collectors.toList());
            r.totalMeals = d.getMeals().size();
        }
        return r;
    }

    public Long getId()                             { return id; }
    public void setId(Long v)                       { this.id = v; }
    public String getName()                         { return name; }
    public void setName(String v)                   { this.name = v; }
    public String getDescription()                  { return description; }
    public void setDescription(String v)            { this.description = v; }
    public Long getPatientId()                      { return patientId; }
    public void setPatientId(Long v)                { this.patientId = v; }
    public String getPatientName()                  { return patientName; }
    public void setPatientName(String v)            { this.patientName = v; }
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
    public String getStatus()                       { return status; }
    public void setStatus(String v)                 { this.status = v; }
    public String getGenerationSource()             { return generationSource; }
    public void setGenerationSource(String v)       { this.generationSource = v; }
    public String getAiPrompt()                     { return aiPrompt; }
    public void setAiPrompt(String v)               { this.aiPrompt = v; }
    public String getAiNotes()                      { return aiNotes; }
    public void setAiNotes(String v)                { this.aiNotes = v; }
    public List<MealResponse> getMeals()            { return meals; }
    public void setMeals(List<MealResponse> v)      { this.meals = v; }
    public Integer getTotalMeals()                  { return totalMeals; }
    public void setTotalMeals(Integer v)            { this.totalMeals = v; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public void setCreatedAt(LocalDateTime v)       { this.createdAt = v; }
    public LocalDateTime getUpdatedAt()             { return updatedAt; }
    public void setUpdatedAt(LocalDateTime v)       { this.updatedAt = v; }
}
