package com.nutriem.dto.response;

import com.nutriem.model.Anthropometry;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnthropometryResponse {

    private Long   id;
    private Long   patientId;
    private LocalDate assessmentDate;

    // Peso / Estatura
    private Double weightKg;
    private Double heightCm;
    private Double bmi;

    // Pliegues
    private Double skinfoldTriceps;
    private Double skinfoldSubscapular;
    private Double skinfoldBiceps;
    private Double skinfoldSuprailiac;
    private Double skinfoldAbdominal;
    private Double skinfoldThigh;
    private Double skinfoldChest;
    private Double skinfoldCalf;
    private Double skinfoldAxillary;

    // Perímetros
    private Double perimeterWaist;
    private Double perimeterHip;
    private Double whrRatio;
    private String whrRisk;
    private Double whtRatio;
    private String whtRisk;
    private Double perimeterNeck;
    private Double perimeterArm;
    private Double perimeterForearm;
    private Double perimeterThigh;
    private Double perimeterCalf;
    private Double perimeterChest;

    // Diámetros
    private Double diameterBiepicondylarHumerus;
    private Double diameterBiepicondylarFemur;
    private Double diameterBistyloiWrist;
    private Double diameterBimalleolarAnkle;

    // Resultados % grasa
    private Double bodyFatPctJp3;
    private Double bodyFatPctJp7;
    private Double bodyFatPctDurnin;
    private Double leanMassKg;
    private Double fatMassKg;

    // Peso teórico
    private Double theoreticalWeightRobinson;
    private Double theoreticalWeightMiller;
    private Double theoreticalWeightDevine;
    private Double theoreticalWeightHamwi;

    // Calorías
    private Double bmrHarrisBenedict;
    private Double bmrMifflin;
    private Double bmrFao;
    private Double tdeeKcal;
    private Double activityFactor;
    private Anthropometry.EnergyBalance  energyBalance;

    // Somatotipo
    private Double somatotypeEndomorphy;
    private Double somatotypeMesomorphy;
    private Double somatotypeEctomorphy;
    private Double somatotypeX;
    private Double somatotypeY;

    private Anthropometry.SkinfoldMethod skinfoldMethod;
    private String notes;
    private LocalDateTime createdAt;

    public static AnthropometryResponse from(Anthropometry a) {
        AnthropometryResponse r = new AnthropometryResponse();
        r.id                    = a.getId();
        r.patientId             = a.getPatient().getId();
        r.assessmentDate        = a.getAssessmentDate();
        r.weightKg              = a.getWeightKg();
        r.heightCm              = a.getHeightCm();
        r.bmi                   = a.getBmi();
        r.skinfoldTriceps       = a.getSkinfoldTriceps();
        r.skinfoldSubscapular   = a.getSkinfoldSubscapular();
        r.skinfoldBiceps        = a.getSkinfoldBiceps();
        r.skinfoldSuprailiac    = a.getSkinfoldSuprailiac();
        r.skinfoldAbdominal     = a.getSkinfoldAbdominal();
        r.skinfoldThigh         = a.getSkinfoldThigh();
        r.skinfoldChest         = a.getSkinfoldChest();
        r.skinfoldCalf          = a.getSkinfoldCalf();
        r.skinfoldAxillary      = a.getSkinfoldAxillary();
        r.perimeterWaist        = a.getPerimeterWaist();
        r.perimeterHip          = a.getPerimeterHip();
        r.whrRatio              = a.getWhrRatio();
        r.whrRisk               = a.getWhrRisk();
        r.whtRatio              = a.getWhtRatio();
        r.whtRisk               = a.getWhtRisk();
        r.perimeterNeck         = a.getPerimeterNeck();
        r.perimeterArm          = a.getPerimeterArm();
        r.perimeterForearm      = a.getPerimeterForearm();
        r.perimeterThigh        = a.getPerimeterThigh();
        r.perimeterCalf         = a.getPerimeterCalf();
        r.perimeterChest        = a.getPerimeterChest();
        r.diameterBiepicondylarHumerus = a.getDiameterBiepicondylarHumerus();
        r.diameterBiepicondylarFemur   = a.getDiameterBiepicondylarFemur();
        r.diameterBistyloiWrist        = a.getDiameterBistyloiWrist();
        r.diameterBimalleolarAnkle     = a.getDiameterBimalleolarAnkle();
        r.bodyFatPctJp3         = a.getBodyFatPctJp3();
        r.bodyFatPctJp7         = a.getBodyFatPctJp7();
        r.bodyFatPctDurnin      = a.getBodyFatPctDurnin();
        r.leanMassKg            = a.getLeanMassKg();
        r.fatMassKg             = a.getFatMassKg();
        r.theoreticalWeightRobinson = a.getTheoreticalWeightRobinson();
        r.theoreticalWeightMiller   = a.getTheoreticalWeightMiller();
        r.theoreticalWeightDevine   = a.getTheoreticalWeightDevine();
        r.theoreticalWeightHamwi    = a.getTheoreticalWeightHamwi();
        r.bmrHarrisBenedict     = a.getBmrHarrisBenedict();
        r.bmrMifflin            = a.getBmrMifflin();
        r.bmrFao                = a.getBmrFao();
        r.tdeeKcal              = a.getTdeeKcal();
        r.activityFactor        = a.getActivityFactor();
        r.energyBalance         = a.getEnergyBalance();
        r.somatotypeEndomorphy  = a.getSomatotypeEndomorphy();
        r.somatotypeMesomorphy  = a.getSomatotypeMesomorphy();
        r.somatotypeEctomorphy  = a.getSomatotypeEctomorphy();
        r.somatotypeX           = a.getSomatotypeX();
        r.somatotypeY           = a.getSomatotypeY();
        r.skinfoldMethod        = a.getSkinfoldMethod();
        r.notes                 = a.getNotes();
        r.createdAt             = a.getCreatedAt();
        return r;
    }

    // Getters
    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public Double getWeightKg() { return weightKg; }
    public Double getHeightCm() { return heightCm; }
    public Double getBmi() { return bmi; }
    public Double getSkinfoldTriceps() { return skinfoldTriceps; }
    public Double getSkinfoldSubscapular() { return skinfoldSubscapular; }
    public Double getSkinfoldBiceps() { return skinfoldBiceps; }
    public Double getSkinfoldSuprailiac() { return skinfoldSuprailiac; }
    public Double getSkinfoldAbdominal() { return skinfoldAbdominal; }
    public Double getSkinfoldThigh() { return skinfoldThigh; }
    public Double getSkinfoldChest() { return skinfoldChest; }
    public Double getSkinfoldCalf() { return skinfoldCalf; }
    public Double getSkinfoldAxillary() { return skinfoldAxillary; }
    public Double getPerimeterWaist() { return perimeterWaist; }
    public Double getPerimeterHip() { return perimeterHip; }
    public Double getPerimeterNeck() { return perimeterNeck; }
    public Double getPerimeterArm() { return perimeterArm; }
    public Double getPerimeterForearm() { return perimeterForearm; }
    public Double getPerimeterThigh() { return perimeterThigh; }
    public Double getPerimeterCalf() { return perimeterCalf; }
    public Double getPerimeterChest() { return perimeterChest; }
    public Double getDiameterBiepicondylarHumerus() { return diameterBiepicondylarHumerus; }
    public Double getDiameterBiepicondylarFemur() { return diameterBiepicondylarFemur; }
    public Double getDiameterBistyloiWrist() { return diameterBistyloiWrist; }
    public Double getDiameterBimalleolarAnkle() { return diameterBimalleolarAnkle; }
    public Double getBodyFatPctJp3() { return bodyFatPctJp3; }
    public Double getBodyFatPctJp7() { return bodyFatPctJp7; }
    public Double getBodyFatPctDurnin() { return bodyFatPctDurnin; }
    public Double getLeanMassKg() { return leanMassKg; }
    public Double getFatMassKg() { return fatMassKg; }
    public Double getTheoreticalWeightRobinson() { return theoreticalWeightRobinson; }
    public Double getTheoreticalWeightMiller() { return theoreticalWeightMiller; }
    public Double getTheoreticalWeightDevine() { return theoreticalWeightDevine; }
    public Double getTheoreticalWeightHamwi() { return theoreticalWeightHamwi; }
    public Double getBmrHarrisBenedict() { return bmrHarrisBenedict; }
    public Double getBmrMifflin() { return bmrMifflin; }
    public Double getBmrFao() { return bmrFao; }
    public Double getTdeeKcal() { return tdeeKcal; }
    public Double getActivityFactor() { return activityFactor; }
    public Anthropometry.EnergyBalance getEnergyBalance() { return energyBalance; }
    public Double getSomatotypeEndomorphy() { return somatotypeEndomorphy; }
    public Double getSomatotypeMesomorphy() { return somatotypeMesomorphy; }
    public Double getSomatotypeEctomorphy() { return somatotypeEctomorphy; }
    public Double getSomatotypeX() { return somatotypeX; }
    public Double getSomatotypeY() { return somatotypeY; }
    public Anthropometry.SkinfoldMethod getSkinfoldMethod() { return skinfoldMethod; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public Double getWhrRatio()  { return whrRatio; }
    public String getWhrRisk()   { return whrRisk; }
    public Double getWhtRatio()  { return whtRatio; }
    public String getWhtRisk()   { return whtRisk; }
}
