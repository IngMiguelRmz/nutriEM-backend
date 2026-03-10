package com.nutriem.dto.request;

import jakarta.validation.constraints.NotNull;

public class AiDietPlanRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    // Optional custom instructions from the nutriologist
    private String customInstructions;

    // Number of days to generate (default 7)
    private Integer days = 7;

    // Calorie target override (if null, Claude will calculate based on patient profile)
    private Integer targetCalories;

    private String planName;

    public AiDietPlanRequest() {}

    public Long getPatientId()                  { return patientId; }
    public void setPatientId(Long v)            { this.patientId = v; }
    public String getCustomInstructions()       { return customInstructions; }
    public void setCustomInstructions(String v) { this.customInstructions = v; }
    public Integer getDays()                    { return days != null ? days : 7; }
    public void setDays(Integer v)              { this.days = v; }
    public Integer getTargetCalories()          { return targetCalories; }
    public void setTargetCalories(Integer v)    { this.targetCalories = v; }
    public String getPlanName()                 { return planName; }
    public void setPlanName(String v)           { this.planName = v; }
}
