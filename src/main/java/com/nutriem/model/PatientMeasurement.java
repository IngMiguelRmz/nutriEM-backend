package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient_measurements")
public class PatientMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false) private LocalDate measuredAt;
    private Double weightKg, heightCm, waistCm, hipCm, bodyFatPercent, muscleMassKg;

    @Column(columnDefinition = "TEXT") private String notes;

    @CreationTimestamp @Column(updatable = false) private LocalDateTime createdAt;

    public PatientMeasurement() {}

    @Transient
    public Double getBmi() {
        if (weightKg == null || heightCm == null || heightCm == 0) return null;
        double h = heightCm / 100.0;
        return Math.round((weightKg / (h * h)) * 10.0) / 10.0;
    }

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public Patient getPatient()                 { return patient; }
    public void setPatient(Patient v)           { this.patient = v; }
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
    public LocalDateTime getCreatedAt()         { return createdAt; }
}
