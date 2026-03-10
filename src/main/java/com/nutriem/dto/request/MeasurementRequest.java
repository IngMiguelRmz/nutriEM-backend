package com.nutriem.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MeasurementRequest {

    @NotNull(message = "Measurement date is required")
    private LocalDate measuredAt;

    private Double weightKg;
    private Double heightCm;
    private Double waistCm;
    private Double hipCm;
    private Double bodyFatPercent;
    private Double muscleMassKg;
    private String notes;

    public MeasurementRequest() {}

    public LocalDate getMeasuredAt()            { return measuredAt; }
    public void setMeasuredAt(LocalDate v)      { this.measuredAt = v; }
    public Double getWeightKg()                 { return weightKg; }
    public void setWeightKg(Double v)           { this.weightKg = v; }
    public Double getHeightCm()                 { return heightCm; }
    public void setHeightCm(Double v)           { this.heightCm = v; }
    public Double getWaistCm()                  { return waistCm; }
    public void setWaistCm(Double v)            { this.waistCm = v; }
    public Double getHipCm()                    { return hipCm; }
    public void setHipCm(Double v)              { this.hipCm = v; }
    public Double getBodyFatPercent()           { return bodyFatPercent; }
    public void setBodyFatPercent(Double v)     { this.bodyFatPercent = v; }
    public Double getMuscleMassKg()             { return muscleMassKg; }
    public void setMuscleMassKg(Double v)       { this.muscleMassKg = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
}
