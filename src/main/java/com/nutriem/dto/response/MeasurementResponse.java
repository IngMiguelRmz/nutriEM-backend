package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nutriem.model.PatientMeasurement;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementResponse {

    private Long          id;
    private Long          patientId;
    private LocalDate     measuredAt;
    private Double        weightKg;
    private Double        heightCm;
    private Double        bmi;
    private Double        waistCm;
    private Double        hipCm;
    private Double        bodyFatPercent;
    private Double        muscleMassKg;
    private String        notes;
    private LocalDateTime createdAt;

    public MeasurementResponse() {}

    public static MeasurementResponse from(PatientMeasurement m) {
        MeasurementResponse r = new MeasurementResponse();
        r.id             = m.getId();
        r.patientId      = m.getPatient() != null ? m.getPatient().getId() : null;
        r.measuredAt     = m.getMeasuredAt();
        r.weightKg       = m.getWeightKg();
        r.heightCm       = m.getHeightCm();
        r.bmi            = m.getBmi();
        r.waistCm        = m.getWaistCm();
        r.hipCm          = m.getHipCm();
        r.bodyFatPercent = m.getBodyFatPercent();
        r.muscleMassKg   = m.getMuscleMassKg();
        r.notes          = m.getNotes();
        r.createdAt      = m.getCreatedAt();
        return r;
    }

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public Long getPatientId()                  { return patientId; }
    public void setPatientId(Long v)            { this.patientId = v; }
    public LocalDate getMeasuredAt()            { return measuredAt; }
    public void setMeasuredAt(LocalDate v)      { this.measuredAt = v; }
    public Double getWeightKg()                 { return weightKg; }
    public void setWeightKg(Double v)           { this.weightKg = v; }
    public Double getHeightCm()                 { return heightCm; }
    public void setHeightCm(Double v)           { this.heightCm = v; }
    public Double getBmi()                      { return bmi; }
    public void setBmi(Double v)                { this.bmi = v; }
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
    public LocalDateTime getCreatedAt()         { return createdAt; }
    public void setCreatedAt(LocalDateTime v)   { this.createdAt = v; }
}
