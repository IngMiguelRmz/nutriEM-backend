package com.nutriem.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequest {
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String title;
    private String notes;
    private String status;

    public LocalDate getAppointmentDate()       { return appointmentDate; }
    public void setAppointmentDate(LocalDate v) { this.appointmentDate = v; }
    public LocalTime getAppointmentTime()       { return appointmentTime; }
    public void setAppointmentTime(LocalTime v) { this.appointmentTime = v; }
    public String getTitle()                    { return title; }
    public void setTitle(String v)              { this.title = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
    public String getStatus()                   { return status; }
    public void setStatus(String v)             { this.status = v; }
}
