package com.nutriem.service;

import com.nutriem.dto.request.AppointmentRequest;
import com.nutriem.dto.response.AppointmentResponse;
import com.nutriem.exception.ResourceNotFoundException;
import com.nutriem.model.Appointment;
import com.nutriem.model.Patient;
import com.nutriem.model.User;
import com.nutriem.repository.AppointmentRepository;
import com.nutriem.repository.PatientRepository;
import com.nutriem.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository     patientRepository;
    private final UserRepository        userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                               PatientRepository patientRepository,
                               UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository     = patientRepository;
        this.userRepository        = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public AppointmentResponse create(Long patientId, AppointmentRequest req) {
        User nutritionist = getCurrentUser();
        Patient patient = patientRepository.findByIdAndNutritionistId(patientId, nutritionist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setNutritionist(nutritionist);
        mapFields(a, req);
        return AppointmentResponse.from(appointmentRepository.save(a));
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getForPatient(Long patientId, LocalDate from, LocalDate to) {
        User nutritionist = getCurrentUser();
        patientRepository.findByIdAndNutritionistId(patientId, nutritionist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientId));

        List<Appointment> list = (from != null && to != null)
                ? appointmentRepository.findByPatientAndNutritionistBetween(patientId, nutritionist.getId(), from, to)
                : appointmentRepository.findByPatientAndNutritionist(patientId, nutritionist.getId());

        return list.stream().map(AppointmentResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse update(Long id, AppointmentRequest req) {
        User nutritionist = getCurrentUser();
        Appointment a = appointmentRepository.findByIdAndNutritionistId(id, nutritionist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        mapFields(a, req);
        return AppointmentResponse.from(appointmentRepository.save(a));
    }

    @Transactional
    public void delete(Long id) {
        User nutritionist = getCurrentUser();
        Appointment a = appointmentRepository.findByIdAndNutritionistId(id, nutritionist.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        appointmentRepository.delete(a);
    }

    // All appointments for nutritionist in date range (global calendar + today widget)
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllForNutritionist(LocalDate from, LocalDate to) {
        User nutritionist = getCurrentUser();
        return appointmentRepository
                .findAllByNutritionistBetween(nutritionist.getId(), from, to)
                .stream().map(AppointmentResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getToday() {
        return getAllForNutritionist(LocalDate.now(), LocalDate.now());
    }

    private void mapFields(Appointment a, AppointmentRequest req) {
        if (req.getAppointmentDate() != null) a.setAppointmentDate(req.getAppointmentDate());
        if (req.getAppointmentTime() != null) a.setAppointmentTime(req.getAppointmentTime());
        if (req.getTitle()           != null) a.setTitle(req.getTitle());
        if (req.getNotes()           != null) a.setNotes(req.getNotes());
        if (req.getStatus()          != null) {
            try { a.setStatus(Appointment.Status.valueOf(req.getStatus())); } catch (Exception ignored) {}
        }
    }
}
