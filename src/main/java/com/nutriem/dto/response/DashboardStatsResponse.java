package com.nutriem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardStatsResponse {

    // ── Patient stats ─────────────────────────────────────────
    private long totalPatients;
    private long activePatients;
    private long newPatientsThisMonth;

    // ── Diet plan stats ───────────────────────────────────────
    private long totalDietPlans;
    private long activeDietPlans;
    private long aiGeneratedPlans;
    private long manualPlans;

    // ── Recent activity ───────────────────────────────────────
    private List<PatientResponse>  recentPatients;
    private List<DietPlanResponse> recentDietPlans;

    // ── Goal breakdown ────────────────────────────────────────
    private List<GoalStat> goalBreakdown;

    public DashboardStatsResponse() {}

    // ── Getters & Setters ─────────────────────────────────────
    public long getTotalPatients()                          { return totalPatients; }
    public void setTotalPatients(long v)                    { this.totalPatients = v; }
    public long getActivePatients()                         { return activePatients; }
    public void setActivePatients(long v)                   { this.activePatients = v; }
    public long getNewPatientsThisMonth()                   { return newPatientsThisMonth; }
    public void setNewPatientsThisMonth(long v)             { this.newPatientsThisMonth = v; }
    public long getTotalDietPlans()                         { return totalDietPlans; }
    public void setTotalDietPlans(long v)                   { this.totalDietPlans = v; }
    public long getActiveDietPlans()                        { return activeDietPlans; }
    public void setActiveDietPlans(long v)                  { this.activeDietPlans = v; }
    public long getAiGeneratedPlans()                       { return aiGeneratedPlans; }
    public void setAiGeneratedPlans(long v)                 { this.aiGeneratedPlans = v; }
    public long getManualPlans()                            { return manualPlans; }
    public void setManualPlans(long v)                      { this.manualPlans = v; }
    public List<PatientResponse> getRecentPatients()        { return recentPatients; }
    public void setRecentPatients(List<PatientResponse> v)  { this.recentPatients = v; }
    public List<DietPlanResponse> getRecentDietPlans()      { return recentDietPlans; }
    public void setRecentDietPlans(List<DietPlanResponse> v){ this.recentDietPlans = v; }
    public List<GoalStat> getGoalBreakdown()                { return goalBreakdown; }
    public void setGoalBreakdown(List<GoalStat> v)          { this.goalBreakdown = v; }

    // ── Inner class for goal breakdown ────────────────────────
    public static class GoalStat {
        private String goal;
        private long   count;

        public GoalStat() {}
        public GoalStat(String goal, long count) {
            this.goal  = goal;
            this.count = count;
        }

        public String getGoal()     { return goal; }
        public void setGoal(String v) { this.goal = v; }
        public long getCount()      { return count; }
        public void setCount(long v){ this.count = v; }
    }
}
