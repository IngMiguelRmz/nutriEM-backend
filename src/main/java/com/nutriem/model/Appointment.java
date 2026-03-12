package com.nutriem.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id", nullable = false)
    private User nutritionist;

    @Column(nullable = false) private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    @Column(columnDefinition = "TEXT") private String notes;
    private String title;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;

    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;

    public Appointment() {}

    public Long getId()                         { return id; }
    public Patient getPatient()                 { return patient; }
    public void setPatient(Patient v)           { this.patient = v; }
    public User getNutritionist()               { return nutritionist; }
    public void setNutritionist(User v)         { this.nutritionist = v; }
    public LocalDate getAppointmentDate()       { return appointmentDate; }
    public void setAppointmentDate(LocalDate v) { this.appointmentDate = v; }
    public LocalTime getAppointmentTime()       { return appointmentTime; }
    public void setAppointmentTime(LocalTime v) { this.appointmentTime = v; }
    public String getNotes()                    { return notes; }
    public void setNotes(String v)              { this.notes = v; }
    public String getTitle()                    { return title; }
    public void setTitle(String v)              { this.title = v; }
    public Status getStatus()                   { return status; }
    public void setStatus(Status v)             { this.status = v; }
    public LocalDateTime getCreatedAt()         { return createdAt; }

    public enum Status { SCHEDULED, COMPLETED, CANCELLED }
}
