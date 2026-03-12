package com.nutriem.repository;

import com.nutriem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
           "AND a.nutritionist.id = :nutritionistId ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findByPatientAndNutritionist(@Param("patientId") Long patientId,
                                                    @Param("nutritionistId") Long nutritionistId);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
           "AND a.nutritionist.id = :nutritionistId " +
           "AND a.appointmentDate BETWEEN :from AND :to " +
           "ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findByPatientAndNutritionistBetween(@Param("patientId") Long patientId,
                                                           @Param("nutritionistId") Long nutritionistId,
                                                           @Param("from") LocalDate from,
                                                           @Param("to") LocalDate to);

    Optional<Appointment> findByIdAndNutritionistId(Long id, Long nutritionistId);

    @Query("SELECT a FROM Appointment a WHERE a.nutritionist.id = :nutritionistId " +
           "AND a.appointmentDate BETWEEN :from AND :to " +
           "ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findAllByNutritionistBetween(@Param("nutritionistId") Long nutritionistId,
                                                    @Param("from") LocalDate from,
                                                    @Param("to") LocalDate to);
}
