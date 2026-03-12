package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anthropometry")
public class Anthropometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDate assessmentDate;

    // ── Peso / Estatura ───────────────────────────────────────
    private Double weightKg;
    private Double heightCm;
    private Double bmi;

    // ── Pliegues cutáneos (mm) ────────────────────────────────
    private Double skinfoldTriceps;
    private Double skinfoldSubscapular;
    private Double skinfoldBiceps;
    private Double skinfoldSuprailiac;
    private Double skinfoldAbdominal;
    private Double skinfoldThigh;
    private Double skinfoldChest;
    private Double skinfoldCalf;
    private Double skinfoldAxillary;

    // ── Perímetros (cm) ───────────────────────────────────────
    private Double perimeterWaist;
    private Double perimeterHip;
    private Double perimeterNeck;
    private Double perimeterArm;
    private Double perimeterForearm;
    private Double perimeterThigh;
    private Double perimeterCalf;
    private Double perimeterChest;

    // ── Diámetros (cm) ────────────────────────────────────────
    private Double diameterBiepicondylarHumerus;
    private Double diameterBiepicondylarFemur;
    private Double diameterBistyloiWrist;
    private Double diameterBimalleolarAnkle;

    // ── Resultados: % Grasa ───────────────────────────────────
    private Double bodyFatPctJp3;        // Jackson-Pollock 3
    private Double bodyFatPctJp7;        // Jackson-Pollock 7
    private Double bodyFatPctDurnin;     // Durnin-Womersley 4
    private Double leanMassKg;
    private Double fatMassKg;

    // ── Resultados: Peso teórico ──────────────────────────────
    private Double theoreticalWeightRobinson;
    private Double theoreticalWeightMiller;
    private Double theoreticalWeightDevine;
    private Double theoreticalWeightHamwi;

    // ── Resultados: Calorías ──────────────────────────────────
    private Double bmrHarrisBenedict;
    private Double bmrMifflin;
    private Double bmrFao;
    private Double tdeeKcal;             // total daily energy expenditure
    private Double activityFactor;
    @Enumerated(EnumType.STRING)
    private EnergyBalance energyBalance; // NORMO, DEFICIT, SURPLUS

    // ── Resultados: Somatotipo ────────────────────────────────
    private Double somatotypeEndomorphy;
    private Double somatotypeMesomorphy;
    private Double somatotypeEctomorphy;
    private Double somatotypeX;          // X axis for somatochart
    private Double somatotypeY;          // Y axis for somatochart

    // ── WHR / WHtR ────────────────────────────────────────────
    private Double whrRatio;             // Waist-to-Hip Ratio
    private String whrRisk;             // Low / Moderate / High / Very High
    private Double whtRatio;            // Waist-to-Height Ratio
    private String whtRisk;             // Slim / Healthy / Overweight / Very High


    // ── Método usado ──────────────────────────────────────────
    @Enumerated(EnumType.STRING)
    private SkinfoldMethod skinfoldMethod;

    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // ── Enums ─────────────────────────────────────────────────
    public enum SkinfoldMethod {
        JACKSON_POLLOCK_3, JACKSON_POLLOCK_7, DURNIN_WOMERSLEY_4
    }

    public enum EnergyBalance {
        NORMO, DEFICIT, SURPLUS
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId() { return id; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient p) { this.patient = p; }
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate d) { this.assessmentDate = d; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double v) { this.weightKg = v; }
    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double v) { this.heightCm = v; }
    public Double getBmi() { return bmi; }
    public void setBmi(Double v) { this.bmi = v; }
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
    public Double getBodyFatPctJp3() { return bodyFatPctJp3; }
    public void setBodyFatPctJp3(Double v) { this.bodyFatPctJp3 = v; }
    public Double getBodyFatPctJp7() { return bodyFatPctJp7; }
    public void setBodyFatPctJp7(Double v) { this.bodyFatPctJp7 = v; }
    public Double getBodyFatPctDurnin() { return bodyFatPctDurnin; }
    public void setBodyFatPctDurnin(Double v) { this.bodyFatPctDurnin = v; }
    public Double getLeanMassKg() { return leanMassKg; }
    public void setLeanMassKg(Double v) { this.leanMassKg = v; }
    public Double getFatMassKg() { return fatMassKg; }
    public void setFatMassKg(Double v) { this.fatMassKg = v; }
    public Double getTheoreticalWeightRobinson() { return theoreticalWeightRobinson; }
    public void setTheoreticalWeightRobinson(Double v) { this.theoreticalWeightRobinson = v; }
    public Double getTheoreticalWeightMiller() { return theoreticalWeightMiller; }
    public void setTheoreticalWeightMiller(Double v) { this.theoreticalWeightMiller = v; }
    public Double getTheoreticalWeightDevine() { return theoreticalWeightDevine; }
    public void setTheoreticalWeightDevine(Double v) { this.theoreticalWeightDevine = v; }
    public Double getTheoreticalWeightHamwi() { return theoreticalWeightHamwi; }
    public void setTheoreticalWeightHamwi(Double v) { this.theoreticalWeightHamwi = v; }
    public Double getBmrHarrisBenedict() { return bmrHarrisBenedict; }
    public void setBmrHarrisBenedict(Double v) { this.bmrHarrisBenedict = v; }
    public Double getBmrMifflin() { return bmrMifflin; }
    public void setBmrMifflin(Double v) { this.bmrMifflin = v; }
    public Double getBmrFao() { return bmrFao; }
    public void setBmrFao(Double v) { this.bmrFao = v; }
    public Double getTdeeKcal() { return tdeeKcal; }
    public void setTdeeKcal(Double v) { this.tdeeKcal = v; }
    public Double getActivityFactor() { return activityFactor; }
    public void setActivityFactor(Double v) { this.activityFactor = v; }
    public EnergyBalance getEnergyBalance() { return energyBalance; }
    public void setEnergyBalance(EnergyBalance v) { this.energyBalance = v; }
    public Double getSomatotypeEndomorphy() { return somatotypeEndomorphy; }
    public void setSomatotypeEndomorphy(Double v) { this.somatotypeEndomorphy = v; }
    public Double getSomatotypeMesomorphy() { return somatotypeMesomorphy; }
    public void setSomatotypeMesomorphy(Double v) { this.somatotypeMesomorphy = v; }
    public Double getSomatotypeEctomorphy() { return somatotypeEctomorphy; }
    public void setSomatotypeEctomorphy(Double v) { this.somatotypeEctomorphy = v; }
    public Double getSomatotypeX() { return somatotypeX; }
    public void setSomatotypeX(Double v) { this.somatotypeX = v; }
    public Double getSomatotypeY() { return somatotypeY; }
    public void setSomatotypeY(Double v) { this.somatotypeY = v; }
    public SkinfoldMethod getSkinfoldMethod() { return skinfoldMethod; }
    public void setSkinfoldMethod(SkinfoldMethod v) { this.skinfoldMethod = v; }
    public String getNotes() { return notes; }
    public void setNotes(String v) { this.notes = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public Double getWhrRatio()  { return whrRatio; }
    public void   setWhrRatio(Double v)  { this.whrRatio = v; }
    public String getWhrRisk()   { return whrRisk; }
    public void   setWhrRisk(String v)   { this.whrRisk = v; }
    public Double getWhtRatio()  { return whtRatio; }
    public void   setWhtRatio(Double v)  { this.whtRatio = v; }
    public String getWhtRisk()   { return whtRisk; }
    public void   setWhtRisk(String v)   { this.whtRisk = v; }
}