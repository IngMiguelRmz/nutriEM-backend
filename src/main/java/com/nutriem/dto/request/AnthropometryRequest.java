package com.nutriem.dto.request;

import com.nutriem.model.Anthropometry;
import java.time.LocalDate;

public class AnthropometryRequest {

    private LocalDate assessmentDate;

    // Peso / Estatura
    private Double weightKg;
    private Double heightCm;

    // Pliegues (mm)
    private Double skinfoldTriceps;
    private Double skinfoldSubscapular;
    private Double skinfoldBiceps;
    private Double skinfoldSuprailiac;
    private Double skinfoldAbdominal;
    private Double skinfoldThigh;
    private Double skinfoldChest;
    private Double skinfoldCalf;
    private Double skinfoldAxillary;

    // Perímetros (cm)
    private Double perimeterWaist;
    private Double perimeterHip;
    private Double perimeterNeck;
    private Double perimeterArm;
    private Double perimeterForearm;
    private Double perimeterThigh;
    private Double perimeterCalf;
    private Double perimeterChest;

    // Diámetros (cm)
    private Double diameterBiepicondylarHumerus;
    private Double diameterBiepicondylarFemur;
    private Double diameterBistyloiWrist;
    private Double diameterBimalleolarAnkle;

    // Config
    private Anthropometry.SkinfoldMethod skinfoldMethod;
    private Anthropometry.EnergyBalance  energyBalance;
    private Double activityFactor;
    private String notes;

    // Getters & Setters
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate v) { this.assessmentDate = v; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double v) { this.weightKg = v; }
    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double v) { this.heightCm = v; }
    public Double getSkinfoldTriceps() { return skinfoldTriceps; }
    public void setSkinfoldTriceps(Double v) { this.skinfoldTriceps = v; }
    public Double getSkinfoldSubscapular() { return skinfoldSubscapular; }
    public void setSkinfoldSubscapular(Double v) { this.skinfoldSubscapular = v; }
    public Double getSkinfoldBiceps() { return skinfoldBiceps; }
    public void setSkinfoldBiceps(Double v) { this.skinfoldBiceps = v; }
    public Double getSkinfoldSuprailiac() { return skinfoldSuprailiac; }
    public void setSkinfoldSuprailiac(Double v) { this.skinfoldSuprailiac = v; }
    public Double getSkinfoldAbdominal() { return skinfoldAbdominal; }
    public void setSkinfoldAbdominal(Double v) { this.skinfoldAbdominal = v; }
    public Double getSkinfoldThigh() { return skinfoldThigh; }
    public void setSkinfoldThigh(Double v) { this.skinfoldThigh = v; }
    public Double getSkinfoldChest() { return skinfoldChest; }
    public void setSkinfoldChest(Double v) { this.skinfoldChest = v; }
    public Double getSkinfoldCalf() { return skinfoldCalf; }
    public void setSkinfoldCalf(Double v) { this.skinfoldCalf = v; }
    public Double getSkinfoldAxillary() { return skinfoldAxillary; }
    public void setSkinfoldAxillary(Double v) { this.skinfoldAxillary = v; }
    public Double getPerimeterWaist() { return perimeterWaist; }
    public void setPerimeterWaist(Double v) { this.perimeterWaist = v; }
    public Double getPerimeterHip() { return perimeterHip; }
    public void setPerimeterHip(Double v) { this.perimeterHip = v; }
    public Double getPerimeterNeck() { return perimeterNeck; }
    public void setPerimeterNeck(Double v) { this.perimeterNeck = v; }
    public Double getPerimeterArm() { return perimeterArm; }
    public void setPerimeterArm(Double v) { this.perimeterArm = v; }
    public Double getPerimeterForearm() { return perimeterForearm; }
    public void setPerimeterForearm(Double v) { this.perimeterForearm = v; }
    public Double getPerimeterThigh() { return perimeterThigh; }
    public void setPerimeterThigh(Double v) { this.perimeterThigh = v; }
    public Double getPerimeterCalf() { return perimeterCalf; }
    public void setPerimeterCalf(Double v) { this.perimeterCalf = v; }
    public Double getPerimeterChest() { return perimeterChest; }
    public void setPerimeterChest(Double v) { this.perimeterChest = v; }
    public Double getDiameterBiepicondylarHumerus() { return diameterBiepicondylarHumerus; }
    public void setDiameterBiepicondylarHumerus(Double v) { this.diameterBiepicondylarHumerus = v; }
    public Double getDiameterBiepicondylarFemur() { return diameterBiepicondylarFemur; }
    public void setDiameterBiepicondylarFemur(Double v) { this.diameterBiepicondylarFemur = v; }
    public Double getDiameterBistyloiWrist() { return diameterBistyloiWrist; }
    public void setDiameterBistyloiWrist(Double v) { this.diameterBistyloiWrist = v; }
    public Double getDiameterBimalleolarAnkle() { return diameterBimalleolarAnkle; }
    public void setDiameterBimalleolarAnkle(Double v) { this.diameterBimalleolarAnkle = v; }
    public Anthropometry.SkinfoldMethod getSkinfoldMethod() { return skinfoldMethod; }
    public void setSkinfoldMethod(Anthropometry.SkinfoldMethod v) { this.skinfoldMethod = v; }
    public Anthropometry.EnergyBalance getEnergyBalance() { return energyBalance; }
    public void setEnergyBalance(Anthropometry.EnergyBalance v) { this.energyBalance = v; }
    public Double getActivityFactor() { return activityFactor; }
    public void setActivityFactor(Double v) { this.activityFactor = v; }
    public String getNotes() { return notes; }
    public void setNotes(String v) { this.notes = v; }
}
