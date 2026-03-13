package com.nutriem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutriem.config.AnthropicConfig;
import com.nutriem.model.ClinicalHistory;
import com.nutriem.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ClaudeAiService {

    private static final Logger log = LoggerFactory.getLogger(ClaudeAiService.class);

    private final HttpClient      httpClient;
    private final AnthropicConfig config;
    private final ObjectMapper    objectMapper;

    public ClaudeAiService(HttpClient httpClient, AnthropicConfig config, ObjectMapper objectMapper) {
        this.httpClient   = httpClient;
        this.config       = config;
        this.objectMapper = objectMapper;
    }

    public JsonNode generateDietPlan(Patient patient, ClinicalHistory history, int days, Integer targetCalories, String customInstructions) {
        String prompt = buildPrompt(patient, history, days, targetCalories, customInstructions);
        String rawResponse = callClaude(prompt);
        return parseJsonFromResponse(rawResponse);
    }

    private String buildPrompt(Patient patient, ClinicalHistory history, int days, Integer targetCalories, String customInstructions) {
        StringBuilder sb = new StringBuilder();

        sb.append("You are a clinical nutritionist AI assistant. Generate a detailed, medically appropriate ");
        sb.append("diet plan based on the following patient profile.\n\n");

        sb.append("## PATIENT PROFILE\n");
        sb.append("Name: ").append(patient.getFullName()).append("\n");

        if (patient.getDateOfBirth() != null) {
            int age = Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears();
            sb.append("Age: ").append(age).append(" years\n");
        }

        sb.append("Gender: ").append(patient.getGender() != null ? patient.getGender().name() : "Not specified").append("\n");

        if (patient.getWeightKg() != null)
            sb.append("Weight: ").append(patient.getWeightKg()).append(" kg\n");
        if (patient.getHeightCm() != null)
            sb.append("Height: ").append(patient.getHeightCm()).append(" cm\n");
        if (patient.getBmi() != null)
            sb.append("BMI: ").append(String.format("%.1f", patient.getBmi())).append("\n");

        sb.append("Activity Level: ").append(patient.getActivityLevel() != null ? patient.getActivityLevel().name() : "Not specified").append("\n");
        sb.append("Primary Goal: ").append(patient.getPrimaryGoal() != null ? patient.getPrimaryGoal().name() : "Not specified").append("\n");

        if (patient.getMedicalConditions() != null && !patient.getMedicalConditions().isBlank())
            sb.append("Medical Conditions: ").append(patient.getMedicalConditions()).append("\n");
        if (patient.getAllergies() != null && !patient.getAllergies().isBlank())
            sb.append("Allergies: ").append(patient.getAllergies()).append("\n");
        if (patient.getFoodPreferences() != null && !patient.getFoodPreferences().isBlank())
            sb.append("Food Preferences: ").append(patient.getFoodPreferences()).append("\n");
        if (patient.getFoodRestrictions() != null && !patient.getFoodRestrictions().isBlank())
            sb.append("Food Restrictions: ").append(patient.getFoodRestrictions()).append("\n");

        if (targetCalories != null)
            sb.append("Target Calories: ").append(targetCalories).append(" kcal/day\n");

        // ── Clinical History ──────────────────────────────────────
        if (history != null) {
            sb.append("\n## CLINICAL HISTORY\n");

            if (history.getChronicDiseases() != null && !history.getChronicDiseases().isBlank())
                sb.append("Chronic Diseases: ").append(history.getChronicDiseases()).append("\n");
            if (history.getFamilyHistory() != null && !history.getFamilyHistory().isBlank())
                sb.append("Family History: ").append(history.getFamilyHistory()).append("\n");
            if (history.getCurrentMedications() != null && !history.getCurrentMedications().isBlank())
                sb.append("Current Medications/Supplements: ").append(history.getCurrentMedications()).append("\n");
            if (history.getSurgicalHistory() != null && !history.getSurgicalHistory().isBlank())
                sb.append("Surgical History: ").append(history.getSurgicalHistory()).append("\n");

            sb.append("\n## EATING HABITS\n");
            if (history.getMealsPerDay() != null)
                sb.append("Meals per day: ").append(history.getMealsPerDay()).append("\n");
            if (Boolean.TRUE.equals(history.getSkipsBreakfast()))
                sb.append("Note: Patient skips breakfast\n");
            if (Boolean.TRUE.equals(history.getEatsLateNight()))
                sb.append("Note: Patient eats late at night\n");
            if (history.getFoodAllergies() != null && !history.getFoodAllergies().isBlank())
                sb.append("Food Allergies: ").append(history.getFoodAllergies()).append("\n");
            if (history.getFoodIntolerances() != null && !history.getFoodIntolerances().isBlank())
                sb.append("Food Intolerances: ").append(history.getFoodIntolerances()).append("\n");
            if (history.getFoodAversions() != null && !history.getFoodAversions().isBlank())
                sb.append("Food Aversions/Dislikes: ").append(history.getFoodAversions()).append("\n");
            if (history.getFoodPreferences() != null && !history.getFoodPreferences().isBlank())
                sb.append("Food Preferences: ").append(history.getFoodPreferences()).append("\n");
            if (history.getDietaryRestrictions() != null && !history.getDietaryRestrictions().isBlank())
                sb.append("Dietary Restrictions: ").append(history.getDietaryRestrictions()).append("\n");
            if (Boolean.TRUE.equals(history.getConsumesAlcohol()) && history.getAlcoholFrequency() != null)
                sb.append("Alcohol: ").append(history.getAlcoholFrequency()).append("\n");
            if (history.getWaterIntakeLiters() != null)
                sb.append("Daily water intake: ").append(history.getWaterIntakeLiters() / 10.0).append(" L\n");

            sb.append("\n## DIGESTIVE HEALTH\n");
            java.util.List<String> digestive = new java.util.ArrayList<>();
            if (Boolean.TRUE.equals(history.getHasBloating()))    digestive.add("bloating");
            if (Boolean.TRUE.equals(history.getHasConstipation())) digestive.add("constipation");
            if (Boolean.TRUE.equals(history.getHasDiarrhea()))     digestive.add("diarrhea");
            if (Boolean.TRUE.equals(history.getHasAcidReflux()))   digestive.add("acid reflux/GERD");
            if (Boolean.TRUE.equals(history.getHasIrritable()))    digestive.add("IBS");
            if (!digestive.isEmpty())
                sb.append("Digestive symptoms: ").append(String.join(", ", digestive)).append("\n");
            if (history.getDigestiveNotes() != null && !history.getDigestiveNotes().isBlank())
                sb.append("Digestive notes: ").append(history.getDigestiveNotes()).append("\n");

            sb.append("\n## LIFESTYLE\n");
            if (history.getSleepHoursPerNight() != null)
                sb.append("Sleep: ").append(history.getSleepHoursPerNight()).append(" hours/night\n");
            if (history.getStressLevel() != null)
                sb.append("Stress level: ").append(history.getStressLevel()).append("\n");
            if (Boolean.TRUE.equals(history.getSmoker()))
                sb.append("Smoker: yes\n");
            if (history.getOccupation() != null && !history.getOccupation().isBlank())
                sb.append("Occupation: ").append(history.getOccupation()).append("\n");
            if (history.getPhysicalActivityDetail() != null && !history.getPhysicalActivityDetail().isBlank())
                sb.append("Physical Activity Detail: ").append(history.getPhysicalActivityDetail()).append("\n");

            // Lab results — only include non-null values
            boolean hasLabs = history.getFastingGlucose() != null || history.getHba1c() != null
                || history.getTotalCholesterol() != null || history.getTriglycerides() != null
                || history.getHemoglobin() != null || history.getVitaminD() != null;
            if (hasLabs) {
                sb.append("\n## RECENT LAB RESULTS\n");
                if (history.getFastingGlucose()   != null) sb.append("Fasting Glucose: ").append(history.getFastingGlucose()).append(" mg/dL\n");
                if (history.getHba1c()            != null) sb.append("HbA1c: ").append(history.getHba1c()).append("%\n");
                if (history.getTotalCholesterol() != null) sb.append("Total Cholesterol: ").append(history.getTotalCholesterol()).append(" mg/dL\n");
                if (history.getLdlCholesterol()   != null) sb.append("LDL: ").append(history.getLdlCholesterol()).append(" mg/dL\n");
                if (history.getHdlCholesterol()   != null) sb.append("HDL: ").append(history.getHdlCholesterol()).append(" mg/dL\n");
                if (history.getTriglycerides()    != null) sb.append("Triglycerides: ").append(history.getTriglycerides()).append(" mg/dL\n");
                if (history.getHemoglobin()       != null) sb.append("Hemoglobin: ").append(history.getHemoglobin()).append(" g/dL\n");
                if (history.getFerritin()         != null) sb.append("Ferritin: ").append(history.getFerritin()).append(" ng/mL\n");
                if (history.getVitaminD()         != null) sb.append("Vitamin D: ").append(history.getVitaminD()).append(" ng/mL\n");
                if (history.getVitaminB12()       != null) sb.append("Vitamin B12: ").append(history.getVitaminB12()).append(" pg/mL\n");
                if (history.getOtherLabResults()  != null && !history.getOtherLabResults().isBlank())
                    sb.append("Other: ").append(history.getOtherLabResults()).append("\n");
            }

            if (history.getWeightGoalDetail() != null && !history.getWeightGoalDetail().isBlank())
                sb.append("\nWeight Goal Detail: ").append(history.getWeightGoalDetail()).append("\n");
            if (history.getPreviousDietAttempts() != null && !history.getPreviousDietAttempts().isBlank())
                sb.append("Previous Diet Attempts: ").append(history.getPreviousDietAttempts()).append("\n");
            if (history.getNutritionistNotes() != null && !history.getNutritionistNotes().isBlank())
                sb.append("\n## NUTRITIONIST NOTES\n").append(history.getNutritionistNotes()).append("\n");
        }

        if (customInstructions != null && !customInstructions.isBlank()) {
            sb.append("\n## ADDITIONAL INSTRUCTIONS FROM NUTRITIONIST\n");
            sb.append(customInstructions).append("\n");
        }

        sb.append("\n## TASK\n");
        sb.append("Generate a ").append(days).append("-day diet plan. ");
        sb.append("Return ONLY a valid JSON object with NO additional text, markdown, or explanation.\n\n");

        sb.append("## REQUIRED JSON FORMAT\n");
        sb.append("{\n");
        sb.append("  \"planName\": \"string\",\n");
        sb.append("  \"planDescription\": \"string - brief clinical rationale\",\n");
        sb.append("  \"targetCalories\": number,\n");
        sb.append("  \"targetProteinG\": number,\n");
        sb.append("  \"targetCarbsG\": number,\n");
        sb.append("  \"targetFatG\": number,\n");
        sb.append("  \"targetFiberG\": number,\n");
        sb.append("  \"aiNotes\": \"string - important clinical notes\",\n");
        sb.append("  \"meals\": [\n");
        sb.append("    {\n");
        sb.append("      \"name\": \"string - meal name (e.g. Oatmeal with Berries)\",\n");
        sb.append("      \"mealType\": \"BREAKFAST|MORNING_SNACK|LUNCH|AFTERNOON_SNACK|DINNER|EVENING_SNACK\",\n");
        sb.append("      \"dayOfWeek\": number (1=Monday to ").append(days).append("),\n");
        sb.append("      \"instructions\": \"string - brief preparation instructions\",\n");
        sb.append("      \"totalCalories\": number,\n");
        sb.append("      \"totalProteinG\": number,\n");
        sb.append("      \"totalCarbsG\": number,\n");
        sb.append("      \"totalFatG\": number,\n");
        sb.append("      \"totalFiberG\": number,\n");
        sb.append("      \"ingredients\": [\n");
        sb.append("        {\n");
        sb.append("          \"name\": \"string - ingredient name (e.g. Rolled oats)\",\n");
        sb.append("          \"quantityGrams\": number,\n");
        sb.append("          \"servingDescription\": \"string - human-readable amount (e.g. 1/2 cup, 80g)\",\n");
        sb.append("          \"calories\": number,\n");
        sb.append("          \"proteinG\": number,\n");
        sb.append("          \"carbsG\": number,\n");
        sb.append("          \"fatG\": number,\n");
        sb.append("          \"fiberG\": number\n");
        sb.append("        }\n");
        sb.append("      ]\n");
        sb.append("    }\n");
        sb.append("  ]\n");
        sb.append("}\n\n");
        sb.append("CRITICAL RULES:\n");
        sb.append("- Every meal MUST have an ingredients array with at least 2 items\n");
        sb.append("- Nutrition values per ingredient must be accurate and sum to the meal totals\n");
        sb.append("- Include at least 3 meals per day (BREAKFAST, LUNCH, DINNER) plus snacks if clinically appropriate\n");
        sb.append("- Ensure all values are appropriate for the patient's conditions and goals");

        return sb.toString();
    }

    private String callClaude(String prompt) {
        try {
            String requestBody = objectMapper.writeValueAsString(
                    new java.util.HashMap<String, Object>() {{
                        put("model", config.getModel());
                        put("max_tokens", config.getMaxTokens());
                        put("messages", java.util.List.of(
                                new java.util.HashMap<String, String>() {{
                                    put("role", "user");
                                    put("content", prompt);
                                }}
                        ));
                    }}
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getApiUrl()))
                    .header("Content-Type", "application/json")
                    .header("x-api-key", config.getApiKey())
                    .header("anthropic-version", "2023-06-01")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            log.info("Calling Claude API for diet plan generation...");
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Claude API error - status: {}, body: {}", response.statusCode(), response.body());
                throw new RuntimeException("Claude API error: " + response.statusCode() + " - " + response.body());
            }

            JsonNode root = objectMapper.readTree(response.body());
            String text = root.path("content").get(0).path("text").asText();
            log.info("Claude API response received successfully");
            return text;

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to call Claude API", e);
            throw new RuntimeException("Failed to call Claude API: " + e.getMessage(), e);
        }
    }

    private JsonNode parseJsonFromResponse(String rawText) {
        try {
            String cleaned = rawText.trim();
            if (cleaned.startsWith("```")) {
                cleaned = cleaned.replaceAll("^```(?:json)?\\s*", "").replaceAll("\\s*```$", "").trim();
            }
            return objectMapper.readTree(cleaned);
        } catch (Exception e) {
            log.error("Failed to parse Claude response as JSON: {}", rawText);
            throw new RuntimeException("Claude returned invalid JSON: " + e.getMessage());
        }
    }
}
