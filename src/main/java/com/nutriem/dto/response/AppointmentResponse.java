package com.nutriem.dto.response;

import com.nutriem.model.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentResponse {
    private Long      id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String    title;
    private String    notes;
    private String    status;
    private Long      patientId;
    private String    patientName;

    public static AppointmentResponse from(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.id              = a.getId();
        r.appointmentDate = a.getAppointmentDate();
        r.appointmentTime = a.getAppointmentTime();
        r.title           = a.getTitle();
        r.notes           = a.getNotes();
        r.status          = a.getStatus().name();
        r.patientId       = a.getPatient().getId();
        r.patientName     = a.getPatient().getFirstName() + " " + a.getPatient().getLastName();
        return r;
    }

    public Long getId()                         { return id; }
    public LocalDate getAppointmentDate()       { return appointmentDate; }
    public LocalTime getAppointmentTime()       { return appointmentTime; }
    public String getTitle()                    { return title; }
    public String getNotes()                    { return notes; }
    public String getStatus()                   { return status; }
    public Long   getPatientId()                 { return patientId; }
    public String getPatientName()               { return patientName; }
}
