package com.nutriem.service;

import com.nutriem.dto.request.AnthropometryRequest;
import com.nutriem.dto.response.AnthropometryResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.Anthropometry;
import com.nutriem.model.Patient;
import com.nutriem.repository.AnthropometryRepository;
import com.nutriem.repository.PatientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnthropometryService {

    private final AnthropometryRepository anthropometryRepository;
    private final PatientRepository       patientRepository;

    public AnthropometryService(AnthropometryRepository anthropometryRepository,
                                PatientRepository patientRepository) {
        this.anthropometryRepository = anthropometryRepository;
        this.patientRepository       = patientRepository;
    }

    // ── Public API ────────────────────────────────────────────

    @Transactional
    public AnthropometryResponse create(Long patientId, AnthropometryRequest req) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Anthropometry a = new Anthropometry();
        a.setPatient(patient);
        a.setAssessmentDate(req.getAssessmentDate() != null ? req.getAssessmentDate() : LocalDate.now());

        // Raw measurements
        mapRawFields(a, req);

        // Derived calculations
        calculateAll(a, patient);

        return AnthropometryResponse.from(anthropometryRepository.save(a));
    }

    @Transactional(readOnly = true)
    public List<AnthropometryResponse> getAll(Long patientId) {
        return anthropometryRepository.findAllByPatientId(patientId)
                .stream().map(AnthropometryResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnthropometryResponse getById(Long id) {
        return AnthropometryResponse.from(
                anthropometryRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Anthropometry record not found")));
    }

    @Transactional
    public AnthropometryResponse update(Long id, AnthropometryRequest req) {
        Anthropometry a = anthropometryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anthropometry record not found"));
        mapRawFields(a, req);
        calculateAll(a, a.getPatient());
        return AnthropometryResponse.from(anthropometryRepository.save(a));
    }

    @Transactional
    public void delete(Long id) {
        if (!anthropometryRepository.existsById(id))
            throw new ResourceNotFoundException("Anthropometry record not found");
        anthropometryRepository.deleteById(id);
    }

    // ── Map raw fields ────────────────────────────────────────

    private void mapRawFields(Anthropometry a, AnthropometryRequest req) {
        a.setWeightKg(req.getWeightKg());
        a.setHeightCm(req.getHeightCm());
        a.setSkinfoldTriceps(req.getSkinfoldTriceps());
        a.setSkinfoldSubscapular(req.getSkinfoldSubscapular());
        a.setSkinfoldBiceps(req.getSkinfoldBiceps());
        a.setSkinfoldSuprailiac(req.getSkinfoldSuprailiac());
        a.setSkinfoldAbdominal(req.getSkinfoldAbdominal());
        a.setSkinfoldThigh(req.getSkinfoldThigh());
        a.setSkinfoldChest(req.getSkinfoldChest());
        a.setSkinfoldCalf(req.getSkinfoldCalf());
        a.setSkinfoldAxillary(req.getSkinfoldAxillary());
        a.setPerimeterWaist(req.getPerimeterWaist());
        a.setPerimeterHip(req.getPerimeterHip());
        a.setPerimeterNeck(req.getPerimeterNeck());
        a.setPerimeterArm(req.getPerimeterArm());
        a.setPerimeterForearm(req.getPerimeterForearm());
        a.setPerimeterThigh(req.getPerimeterThigh());
        a.setPerimeterCalf(req.getPerimeterCalf());
        a.setPerimeterChest(req.getPerimeterChest());
        a.setDiameterBiepicondylarHumerus(req.getDiameterBiepicondylarHumerus());
        a.setDiameterBiepicondylarFemur(req.getDiameterBiepicondylarFemur());
        a.setDiameterBistyloiWrist(req.getDiameterBistyloiWrist());
        a.setDiameterBimalleolarAnkle(req.getDiameterBimalleolarAnkle());
        a.setSkinfoldMethod(req.getSkinfoldMethod());
        a.setEnergyBalance(req.getEnergyBalance());
        a.setActivityFactor(req.getActivityFactor());
        a.setNotes(req.getNotes());
    }

    // ── Master calculation dispatcher ─────────────────────────

    private void calculateAll(Anthropometry a, Patient patient) {
        Double weight = a.getWeightKg();
        Double height = a.getHeightCm();
        com.nutriem.model.Patient.Gender gender = patient.getGender();
        Integer age   = patient.getAge();

        // BMI
        if (weight != null && height != null && height > 0) {
            double h = height / 100.0;
            a.setBmi(round2(weight / (h * h)));
        }

        // Body fat % — try all available methods
        if (age != null && gender != null) {
            calcBodyFat(a, gender, age, weight);
        }

        // Theoretical weight
        if (height != null && gender != null) {
            calcTheoreticalWeight(a, gender, height);
        }

        // BMR / TDEE
        if (weight != null && height != null && age != null && gender != null) {
            calcCalories(a, gender, weight, height, age);
        }

        // Somatotype (requires skinfolds + perimeters + diameters + height + weight)
        calcSomatotype(a, height, weight);
    }

    // ── Body Fat % ────────────────────────────────────────────

    private void calcBodyFat(Anthropometry a, com.nutriem.model.Patient.Gender gender, int age, Double weight) {
        boolean male = gender == com.nutriem.model.Patient.Gender.MALE;

        // Jackson-Pollock 3 sites
        Double jp3density = null;
        if (male) {
            Double chest = a.getSkinfoldChest(), abdom = a.getSkinfoldAbdominal(), thigh = a.getSkinfoldThigh();
            if (allPresent(chest, abdom, thigh)) {
                double sum = chest + abdom + thigh;
                jp3density = 1.10938 - (0.0008267 * sum) + (0.0000016 * sum * sum) - (0.0002574 * age);
            }
        } else {
            Double triceps = a.getSkinfoldTriceps(), suprailiac = a.getSkinfoldSuprailiac(), thigh = a.getSkinfoldThigh();
            if (allPresent(triceps, suprailiac, thigh)) {
                double sum = triceps + suprailiac + thigh;
                jp3density = 1.0994921 - (0.0009929 * sum) + (0.0000023 * sum * sum) - (0.0001392 * age);
            }
        }
        if (jp3density != null) {
            double pct = ((4.95 / jp3density) - 4.50) * 100;
            a.setBodyFatPctJp3(round2(pct));
            setBodyComposition(a, pct, weight);
        }

        // Jackson-Pollock 7 sites
        Double chest = a.getSkinfoldChest(), midAxillary = a.getSkinfoldAxillary(),
               triceps = a.getSkinfoldTriceps(), subscapular = a.getSkinfoldSubscapular(),
               abdom = a.getSkinfoldAbdominal(), suprailiac = a.getSkinfoldSuprailiac(),
               thigh = a.getSkinfoldThigh();
        if (allPresent(chest, midAxillary, triceps, subscapular, abdom, suprailiac, thigh)) {
            double sum = chest + midAxillary + triceps + subscapular + abdom + suprailiac + thigh;
            double jp7density;
            if (male) {
                jp7density = 1.112 - (0.00043499 * sum) + (0.00000055 * sum * sum) - (0.00028826 * age);
            } else {
                jp7density = 1.097 - (0.00046971 * sum) + (0.00000056 * sum * sum) - (0.00012828 * age);
            }
            double pct = ((4.95 / jp7density) - 4.50) * 100;
            a.setBodyFatPctJp7(round2(pct));
            if (a.getBodyFatPctJp3() == null) setBodyComposition(a, pct, weight);
        }

        // Durnin-Womersley 4 sites
        Double biceps = a.getSkinfoldBiceps();
        subscapular = a.getSkinfoldSubscapular();
        suprailiac  = a.getSkinfoldSuprailiac();
        triceps     = a.getSkinfoldTriceps();
        if (allPresent(biceps, subscapular, suprailiac, triceps)) {
            double logSum = Math.log10(biceps + subscapular + suprailiac + triceps);
            double durnin;
            if (male) {
                if      (age < 17) durnin = 1.1533 - (0.0643 * logSum);
                else if (age < 20) durnin = 1.1620 - (0.0630 * logSum);
                else if (age < 30) durnin = 1.1631 - (0.0632 * logSum);
                else if (age < 40) durnin = 1.1422 - (0.0544 * logSum);
                else if (age < 50) durnin = 1.1620 - (0.0700 * logSum);
                else               durnin = 1.1715 - (0.0779 * logSum);
            } else {
                if      (age < 17) durnin = 1.1369 - (0.0598 * logSum);
                else if (age < 20) durnin = 1.1549 - (0.0678 * logSum);
                else if (age < 30) durnin = 1.1599 - (0.0717 * logSum);
                else if (age < 40) durnin = 1.1423 - (0.0632 * logSum);
                else if (age < 50) durnin = 1.1333 - (0.0612 * logSum);
                else               durnin = 1.1339 - (0.0645 * logSum);
            }
            double pct = ((4.95 / durnin) - 4.50) * 100;
            a.setBodyFatPctDurnin(round2(pct));
            if (a.getBodyFatPctJp3() == null && a.getBodyFatPctJp7() == null)
                setBodyComposition(a, pct, weight);
        }
    }

    private void setBodyComposition(Anthropometry a, double fatPct, Double weight) {
        if (weight == null) return;
        double fatMass  = weight * (fatPct / 100.0);
        double leanMass = weight - fatMass;
        a.setFatMassKg(round2(fatMass));
        a.setLeanMassKg(round2(leanMass));
    }

    // ── Theoretical Weight ────────────────────────────────────

    private void calcTheoreticalWeight(Anthropometry a, com.nutriem.model.Patient.Gender gender, double heightCm) {
        boolean male = gender == com.nutriem.model.Patient.Gender.MALE;
        double inchesOver5ft = (heightCm / 2.54) - 60;

        // Robinson (1983)
        double robinson = male
            ? 52.0 + 1.9 * inchesOver5ft
            : 49.0 + 1.7 * inchesOver5ft;
        a.setTheoreticalWeightRobinson(round2(robinson));

        // Miller (1983)
        double miller = male
            ? 56.2 + 1.41 * inchesOver5ft
            : 53.1 + 1.36 * inchesOver5ft;
        a.setTheoreticalWeightMiller(round2(miller));

        // Devine (1974)
        double devine = male
            ? 50.0 + 2.3 * inchesOver5ft
            : 45.5 + 2.3 * inchesOver5ft;
        a.setTheoreticalWeightDevine(round2(devine));

        // Hamwi (1964)
        double hamwi = male
            ? 48.0 + 2.7 * inchesOver5ft
            : 45.5 + 2.2 * inchesOver5ft;
        a.setTheoreticalWeightHamwi(round2(hamwi));
    }

    // ── BMR / TDEE ────────────────────────────────────────────

    private void calcCalories(Anthropometry a, com.nutriem.model.Patient.Gender gender, double weight,
                               double height, int age) {
        boolean male = gender == com.nutriem.model.Patient.Gender.MALE;

        // Harris-Benedict (1919, revised)
        double hb = male
            ? 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
            : 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        a.setBmrHarrisBenedict(round2(hb));

        // Mifflin-St Jeor (1990)
        double mifflin = male
            ? (10 * weight) + (6.25 * height) - (5 * age) + 5
            : (10 * weight) + (6.25 * height) - (5 * age) - 161;
        a.setBmrMifflin(round2(mifflin));

        // FAO/WHO/UNU (1985) — simplified
        double fao;
        if (male) {
            if      (age < 3)  fao = (60.9  * weight) - 54;
            else if (age < 10) fao = (22.7  * weight) + 495;
            else if (age < 18) fao = (17.5  * weight) + 651;
            else if (age < 30) fao = (15.3  * weight) + 679;
            else if (age < 60) fao = (11.6  * weight) + 879;
            else               fao = (13.5  * weight) + 487;
        } else {
            if      (age < 3)  fao = (61.0  * weight) - 51;
            else if (age < 10) fao = (22.5  * weight) + 499;
            else if (age < 18) fao = (12.2  * weight) + 746;
            else if (age < 30) fao = (14.7  * weight) + 496;
            else if (age < 60) fao = (8.7   * weight) + 829;
            else               fao = (10.5  * weight) + 596;
        }
        a.setBmrFao(round2(fao));

        // TDEE — use Mifflin as base, apply activity factor
        double af = a.getActivityFactor() != null ? a.getActivityFactor() : 1.2;
        a.setActivityFactor(af);
        a.setTdeeKcal(round2(mifflin * af));
    }

    // ── Somatotype (Heath-Carter) ─────────────────────────────

    private void calcSomatotype(Anthropometry a, Double height, Double weight) {
        // Endomorphy — requires triceps + subscapular + suprailiac
        Double tri = a.getSkinfoldTriceps(), sub = a.getSkinfoldSubscapular(),
               sup = a.getSkinfoldSuprailiac();
        if (allPresent(tri, sub, sup) && height != null) {
            double sumSkf = tri + sub + sup;
            double corrected = sumSkf * (170.18 / height);
            double endo = -0.7182 + (0.1451 * corrected)
                        - (0.00068 * corrected * corrected)
                        + (0.0000014 * Math.pow(corrected, 3));
            a.setSomatotypeEndomorphy(round2(Math.max(0, endo)));
        }

        // Mesomorphy — requires height, weight, humerus, femur, arm perimeter, calf perimeter, triceps, calf skinfold
        Double humDiam  = a.getDiameterBiepicondylarHumerus();
        Double femDiam  = a.getDiameterBiepicondylarFemur();
        Double armPer   = a.getPerimeterArm();
        Double calfPer  = a.getPerimeterCalf();
        Double calfSkf  = a.getSkinfoldCalf();
        if (allPresent(height, weight, humDiam, femDiam, armPer, calfPer, tri, calfSkf)) {
            double corrArm  = armPer  - (tri    / 10.0);
            double corrCalf = calfPer - (calfSkf / 10.0);
            double meso = (0.858 * humDiam) + (0.601 * femDiam)
                        + (0.188 * corrArm) + (0.161 * corrCalf)
                        - (0.131 * height)  + 4.50;
            a.setSomatotypeMesomorphy(round2(Math.max(0, meso)));
        }

        // Ectomorphy — requires height and weight (HWR)
        if (height != null && weight != null && weight > 0) {
            double hwr = height / Math.pow(weight, 1.0 / 3.0);
            double ecto;
            if      (hwr > 40.75) ecto = (0.732 * hwr) - 28.58;
            else if (hwr > 38.25) ecto = (0.463 * hwr) - 17.63;
            else                  ecto = 0.1;
            a.setSomatotypeEctomorphy(round2(Math.max(0.1, ecto)));
        }

        // Somatochart X/Y axes
        Double endo = a.getSomatotypeEndomorphy();
        Double meso = a.getSomatotypeMesomorphy();
        Double ecto = a.getSomatotypeEctomorphy();
        if (allPresent(endo, meso, ecto)) {
            a.setSomatotypeX(round2(ecto - endo));
            a.setSomatotypeY(round2(2.0 * meso - (ecto + endo)));
        }
    }

    // ── Helpers ───────────────────────────────────────────────

    private boolean allPresent(Double... vals) {
        for (Double v : vals) if (v == null) return false;
        return true;
    }

    private double round2(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
