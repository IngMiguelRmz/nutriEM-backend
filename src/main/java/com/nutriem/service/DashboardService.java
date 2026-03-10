package com.nutriem.service;

import com.nutriem.dto.response.DashboardStatsResponse;
import com.nutriem.dto.response.DietPlanResponse;
import com.nutriem.dto.response.PatientResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.DietPlan;
import com.nutriem.model.User;
import com.nutriem.repository.DietPlanRepository;
import com.nutriem.repository.PatientRepository;
import com.nutriem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final PatientRepository  patientRepository;
    private final DietPlanRepository dietPlanRepository;
    private final UserRepository     userRepository;

    public DashboardService(PatientRepository patientRepository,
                            DietPlanRepository dietPlanRepository,
                            UserRepository userRepository) {
        this.patientRepository  = patientRepository;
        this.dietPlanRepository = dietPlanRepository;
        this.userRepository     = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }

    @Transactional(readOnly = true)
    public DashboardStatsResponse getStats() {
        User nutriologist = getCurrentUser();
        Long uid = nutriologist.getId();
        log.info("Loading dashboard stats for: {}", nutriologist.getEmail());

        DashboardStatsResponse stats = new DashboardStatsResponse();

        // ── Patient counts ────────────────────────────────────
        stats.setTotalPatients(patientRepository.countByNutritionistId(uid));
        stats.setActivePatients(patientRepository.countActiveByNutritionistId(uid));

        LocalDateTime firstOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        stats.setNewPatientsThisMonth(
                patientRepository.countByNutritionistIdAndCreatedAtAfter(uid, firstOfMonth));

        // ── Diet plan counts ──────────────────────────────────
        stats.setTotalDietPlans(dietPlanRepository.countByCreatedById(uid));
        stats.setActiveDietPlans(dietPlanRepository.countActiveByCreatedById(uid));
        stats.setAiGeneratedPlans(dietPlanRepository.countByCreatedByIdAndGenerationSource(
                uid, DietPlan.GenerationSource.AI_GENERATED));
        stats.setManualPlans(dietPlanRepository.countByCreatedByIdAndGenerationSource(
                uid, DietPlan.GenerationSource.MANUAL));

        // ── Recent patients (last 5) ──────────────────────────
        List<PatientResponse> recentPatients = patientRepository
                .findTop5ByNutritionistId(uid, PageRequest.of(0, 5))
                .stream()
                .map(PatientResponse::from)
                .collect(Collectors.toList());
        stats.setRecentPatients(recentPatients);

        // ── Recent diet plans (last 5) ────────────────────────
        List<DietPlanResponse> recentPlans = dietPlanRepository
                .findTop5ByCreatedById(uid, PageRequest.of(0, 5))
                .stream()
                .map(DietPlanResponse::from)
                .collect(Collectors.toList());
        stats.setRecentDietPlans(recentPlans);

        // ── Goal breakdown ────────────────────────────────────
        List<DashboardStatsResponse.GoalStat> goalStats = new ArrayList<>();
        for (Object[] row : patientRepository.countByGoalForNutritionist(uid)) {
            String goal  = row[0] != null ? row[0].toString() : "UNKNOWN";
            long   count = ((Number) row[1]).longValue();
            goalStats.add(new DashboardStatsResponse.GoalStat(goal, count));
        }
        stats.setGoalBreakdown(goalStats);

        return stats;
    }
}
